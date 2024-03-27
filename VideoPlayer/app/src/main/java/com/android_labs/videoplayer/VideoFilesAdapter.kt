package com.android_labs.videoplayer

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.SystemClock
import android.provider.MediaStore
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android_labs.videoplayer.activities.VideoPlayerActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File

class VideoFilesAdapter(private var context: Context, private var videos: MutableList<MediaFiles>): RecyclerView.Adapter<VideoFilesAdapter.ViewHolder>() {

    private lateinit var bottomSheetDialog: BottomSheetDialog

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {

        private var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
        private var videoMenuMore = view.findViewById<ImageView>(R.id.videoMenuMore)

        private var videoName = view.findViewById<TextView>(R.id.videoName)
        private var videoSize = view.findViewById<TextView>(R.id.videoSize)
        private var videoDuration = view.findViewById<TextView>(R.id.videoDuration)

        private var currIndex: Int = -1
        private lateinit var currModel: MediaFiles

        init {

            this.videoMenuMore.setOnClickListener {
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetTheme)

                var bsView = LayoutInflater.from(context).inflate(
                    R.layout.video_bottom_sheet_layout, it.findViewById(R.id.bottomSheetLayout)
                )

                bsView.findViewById<LinearLayout>(R.id.bs_play).setOnClickListener {
                    itemView.performClick()
                    bottomSheetDialog.dismiss()
                }

                bsView.findViewById<LinearLayout>(R.id.bs_edit).setOnClickListener {
                    var alertDialog = AlertDialog.Builder(context)

                    alertDialog.setTitle("Rename to")

                    var editText = EditText(context)
                    var file = File(currModel.path)
                    var videoName = file.name.substring(0 , file.name.lastIndexOf("."))

                    editText.setText(videoName)

                    alertDialog.setView(editText)
                    editText.requestFocus()

                    alertDialog.setPositiveButton("Ok"
                    ) { _, _ ->
                        var onlyPath = file.parentFile.absolutePath
                        var ext = file.absolutePath.substring(file.absolutePath.lastIndexOf("."))
                        var newPath = onlyPath + "/" + editText.text.toString() + ext
                        var newFile = File(newPath)

                        if (file.renameTo(newFile)) {
                            context.applicationContext.contentResolver.delete(
                                MediaStore.Files.getContentUri("external"),
                                MediaStore.MediaColumns.DATA + "=?",
                                arrayOf(file.absolutePath)
                            )

                            context.applicationContext.sendBroadcast(
                                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(newFile))
                            )

                            notifyDataSetChanged()

                            Toast.makeText(context, "Video Renamed Success", Toast.LENGTH_SHORT).show()

                            SystemClock.sleep(200)

                            (context as Activity).recreate()
                        } else {

                            Toast.makeText(context, "Video Renamed Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.create().show()
                    bottomSheetDialog.dismiss()
                }

                bsView.findViewById<LinearLayout>(R.id.bs_share).setOnClickListener {
                    var uri = Uri.parse(currModel.path)
                    var shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.setType("video/*")
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

                    context.startActivity(Intent.createChooser(shareIntent, "Share Video via "))
                    bottomSheetDialog.dismiss()
                }

                bsView.findViewById<LinearLayout>(R.id.bs_delete).setOnClickListener {
                    var alertDialog = AlertDialog.Builder(context)

                    alertDialog.setTitle("Delete Video")
                    alertDialog.setMessage("Do you want to delete this video")

                    alertDialog.setPositiveButton("Delete"
                    ) { dialog, _ ->
                        var uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, currModel.id!!.toLong())
                        var file = File(currModel.path)

                        if (file.delete()) {
                            context.contentResolver.delete(uri, null, null)
                            videos.removeAt(currIndex)
                            notifyItemRemoved(currIndex)
                            notifyItemRangeChanged(currIndex, videos.size)
                            Toast.makeText(context, "Video Delete Success", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(context, "Video Delete Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.show()
                    bottomSheetDialog.dismiss()
                }

                bsView.findViewById<LinearLayout>(R.id.bs_prop).setOnClickListener {
                    var alertDialog = AlertDialog.Builder(context)

                    alertDialog.setTitle("Video Properties")

                    var p1 = "File Name: " + currModel.displayName
                    var p2 = "Path: " + currModel.path!!.substring(0, currModel.path!!.lastIndexOf("/"))
                    var p3 = "Size: " + Formatter.formatFileSize(context, currModel.size!!.toLong())
                    var p4 = "Duration: " + timeConversion(currModel.duration!!.toInt())
                    var p5 = "Format: " + currModel.displayName!!.substring(currModel.displayName!!.lastIndexOf("."))

                    var retriver = MediaMetadataRetriever()
                    retriver.setDataSource(currModel.path!!)

                    var height = retriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                    var width = retriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)

                    var p6 = "Resolution: " +  width + "x" + height

                    alertDialog.setMessage(p1 + "\n\n" + p2 + "\n\n" + p3 + "\n\n" + p4 + "\n\n" + p5 + "\n\n" + p6 + "\n\n")

                    alertDialog.setPositiveButton("Ok"
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }

                    alertDialog.show()
                    bottomSheetDialog.dismiss()
                }

                bottomSheetDialog.setContentView(bsView)
                bottomSheetDialog.show()
            }
        }

        fun bind(model: MediaFiles, videoIndex: Int) {
            this.currIndex = videoIndex
            this.currModel = model

            this.videoName.text = model.displayName
            this.videoSize.text = model.size?.let {
                Formatter.formatFileSize(view.context, it.toLong())
            }
            this.videoDuration.text = model.duration?.let {
                timeConversion(it.toInt())
            }

            Glide.with(view.context).load(File(model.path)).into(this.thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.videos[position], position)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, VideoPlayerActivity::class.java)

            intent.putExtra("videoPosition", position)
            intent.putExtra("videoTitle", this.videos[position].displayName)
            intent.putParcelableArrayListExtra("videoPlayerList", ArrayList(this.videos))

            context.startActivity(intent)
        }
    }

    private fun timeConversion(duration: Int): String {
        var rsult = "";

        var hrs = duration / (3600 * 1000)
        var mins = (duration / 60000) % 60000
        var secs = duration % 60000 / 1000

        if (hrs > 0) {
            rsult = String.format("%02d:%02d:%02d", hrs, mins, secs)
        } else {
            rsult = String.format("%02d:%02d", mins, secs)
        }

        return rsult
    }
}