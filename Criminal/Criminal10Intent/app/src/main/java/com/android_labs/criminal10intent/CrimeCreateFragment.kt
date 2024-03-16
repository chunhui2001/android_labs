package com.android_labs.criminal10intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

private const val ARG_CRIME_ID = "com.android_labs.criminal10intent::crime_id"
private const val DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss"

private const val TAG = "CrimeCreateFragment"

private const val DATE_PICKET_REQUEST_CODE = 0
private const val DATE_PICKET_TAG = "com.android_labs.criminal10intent::date_picker_tag"

private const val CHOOSE_CONTRACT_CODE = 1
private const val REQUEST_PHOTO = 2

class CrimeCreateFragment : Fragment(), DatePickerFragment.Callbacks {

    companion object {
        fun newInstance(crimeId: UUID): CrimeCreateFragment {
            return CrimeCreateFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CRIME_ID, crimeId)
                }
            }
        }

        fun toDateString(date: Date): String {
            return SimpleDateFormat(DATE_FORMATTER).format(date)
        }

        fun fromDateString(date: String): Date {
            return SimpleDateFormat(DATE_FORMATTER).parse(date)
        }
    }

    private lateinit var crimeTitleField: EditText
    private lateinit var crimeDateField: Button
    private lateinit var crimeIsSolvedField: CheckBox
    private lateinit var btnChooseSuspect: Button
    private lateinit var crimeSendReportField: Button
    private lateinit var crimePhotoView: ImageView
    private lateinit var crimePhotoCamera: ImageButton

    private lateinit var photoFile: File
    private lateinit var photoUri: Uri

    private val crimeDetailsViewModel: CrimeDetailsViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getSerializable(ARG_CRIME_ID).let {
            crimeDetailsViewModel.loadCrime(it as UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_create, container, false)

        this.crimeTitleField = view.findViewById(R.id.field_crime_title_et)
        this.crimeDateField = view.findViewById(R.id.field_crime_date_btn)
        this.crimeIsSolvedField = view.findViewById(R.id.field_crime_is_solved_btn)
        this.btnChooseSuspect = view.findViewById(R.id.btn_choose_suspect)
        this.crimeSendReportField = view.findViewById(R.id.btn_send_report_message)
        this.crimePhotoView = view.findViewById(R.id.crime_photo)
        this.crimePhotoCamera = view.findViewById(R.id.crime_camera)

        this.crimeDateField.text = toDateString(Date())

        return view
    }

    private fun getCrimeReport(): String {
        val solvedString = if (this.crimeIsSolvedField.isChecked) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }

        val dateString = this.crimeDateField.text.toString()

        val _suspect = this.btnChooseSuspect.text.toString()

        val suspect = if (_suspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, _suspect)
        }

        return getString(
            R.string.crime_report,
            this.crimeTitleField.text.toString(), dateString, solvedString, suspect
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.crimeDetailsViewModel.crimeLiveData.observe(viewLifecycleOwner, Observer { crime ->

            crime?.let {
                this.crimeTitleField.setText(it.title)

                if (it.suspect.trim().isNotEmpty()) {
                    this.btnChooseSuspect.text = it.suspect.trim()
                }

                this.crimeDateField.text = toDateString(it.date)
                this.crimeIsSolvedField.isChecked = it.isSolved
                this.photoFile = crimeDetailsViewModel.getPhotoFile(it)

                this.photoUri = FileProvider.getUriForFile(
                    requireActivity(), "com.android_labs.criminal10intent.fileprovider", photoFile
                )

                if (photoFile.exists()) {
                    val bitmap = getScaledBitmap(photoFile.path, requireActivity())
                    crimePhotoView.setImageBitmap(bitmap)
                } else {
                    crimePhotoView.setImageDrawable(null)
                }
            }

            if (crime == null) {
                this.photoFile = crimeDetailsViewModel.getPhotoFile(CrimeEntity())

                this.photoUri = FileProvider.getUriForFile(
                    requireActivity(), "com.android_labs.criminal10intent.fileprovider", photoFile
                )
            }
        })
    }

    override fun onStart() {
        super.onStart()

        this.crimeDateField.setOnClickListener { _: View ->
            // 显示日期控件
            DatePickerFragment.newInstance(fromDateString(this.crimeDateField.text.toString()))
                .apply {
                    setTargetFragment(this@CrimeCreateFragment, DATE_PICKET_REQUEST_CODE)
                    show(this@CrimeCreateFragment.requireFragmentManager(), DATE_PICKET_TAG)
                }
        }

        this.btnChooseSuspect.apply {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)

            setOnClickListener { _: View ->
                startActivityForResult(intent, CHOOSE_CONTRACT_CODE)
            }

            // 检查当前应用是否安装了联系人应用
            var packageManager: PackageManager = requireActivity().packageManager
            var resolvedActivity: ResolveInfo? = packageManager.resolveActivity(intent, PackageManager.MATCH_ALL)

            if (resolvedActivity == null) {
//                isEnabled = false // 将当前按钮禁用
            }
        }

        this.crimeSendReportField.apply {
            setOnClickListener { _: View ->
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
                    putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                }.also { intent ->
                    startActivity(intent)
                }
            }
        }

        this.crimePhotoCamera.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            captureImage.addCategory(Intent.CATEGORY_HOME) // 调试用

            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)

            if (resolvedActivity == null) {
                isEnabled = false
            }

            setOnClickListener { _: View ->
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(
                    captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )

                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }

                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }
    }

    private fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)

        return getScaleBitmap(path, size.x, size.y)
    }

    private fun getScaleBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        var inSampleSize = 1

        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            val simpleScale = if (heightScale > widthScale) {
                heightScale
            } else {
                widthScale
            }

            inSampleSize = Math.round(simpleScale)
        }

        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize

        return BitmapFactory.decodeFile(path, options)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    override fun onStop() {
        super.onStop()

        this.crimeDetailsViewModel.saveCrime(
            this.crimeTitleField.text.toString(),
            this.btnChooseSuspect.text.toString(),
            fromDateString(this.crimeDateField.text.toString()),
            this.crimeIsSolvedField.isChecked
        )
    }

    override fun onDateSelected(date: Date) {
        this.crimeDateField.text = toDateString(date)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            CHOOSE_CONTRACT_CODE -> {
                if (data != null) {
                    // 取得联系人名字
                    var contactUri: Uri? = data.data

                    contactUri?.let {
                        var queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                        var cursor = requireActivity().contentResolver.query(contactUri, queryFields, null, null, null)

                        cursor?.use {
                            if (it.count == 0) {
                                return
                            }

                            it.moveToFirst()

                            this.btnChooseSuspect.text = it.getString(0) // 联系人名字

                            this.crimeDetailsViewModel.saveCrime(
                                this.crimeTitleField.text.toString(),
                                this.btnChooseSuspect.text.toString(),
                                fromDateString(this.crimeDateField.text.toString()),
                                this.crimeIsSolvedField.isChecked
                            )
                        }
                    }
                }
            }
            REQUEST_PHOTO -> {
                // 收回权限
                requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                if (photoFile.exists()) {
                    val bitmap = getScaledBitmap(photoFile.path, requireActivity())
                    crimePhotoView.setImageBitmap(bitmap)
                } else {
                    crimePhotoView.setImageDrawable(null)
                }
            }
        }
    }
}