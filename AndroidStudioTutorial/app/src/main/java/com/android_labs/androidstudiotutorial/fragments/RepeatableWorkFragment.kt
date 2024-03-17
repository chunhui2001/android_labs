package com.android_labs.androidstudiotutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.android_labs.androidstudiotutorial.CustomerWork
import com.android_labs.androidstudiotutorial.R
import java.time.Duration
import java.util.concurrent.TimeUnit

class RepeatableWorkFragment : Fragment() {

    private val workManager: WorkManager by lazy { WorkManager.getInstance(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        workManager.enqueueUniquePeriodicWork(
            "myWork",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            PeriodicWorkRequestBuilder<CustomerWork>(
                repeatInterval = 1, // 设置为1，表示每隔1个时间单位执行一次
                repeatIntervalTimeUnit = TimeUnit.SECONDS, // 设置时间单位为秒
                flexTimeInterval = 15, // 灵活执行时间间隔为0，表示无灵活性
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .setRequiresCharging(true)
                    .build()
            ).build()
        )

//        workManager.getWorkInfosForUniqueWorkLiveData("myWork").observe(viewLifecycleOwner) {
//            it.forEach { workInfo ->
//                Log.d("RepeatableWorkFragment", "${workInfo.state}")
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_repeatable_work, container, false)
        return view
    }
}
