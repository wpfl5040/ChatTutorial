package com.wpfl5.chattutorial.utils

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService


class MyJobService : JobService() {
    /*
    * 이 서비스는 Handler응용 프로그램의 주 스레드 에서 실행중인 각 수신 작업을 실행합니다.
    * 즉, 실행 논리를 선택한 스레드 / 처리기 / 다른 스레드로 오프로드 해야합니다AsyncTask . 그렇게하지 않으면 JobManager에서 향후 콜백을 차단하게됩니다.
    * 구체적으로 onStopJob(android.app.job.JobParameters)일정 요구 사항이 더 이상 충족되지 않는다는 것을 알리기위한 것입니다.*/
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        //작업이 실행되기 시작했음을 나타 내기 위해 호출됩니다.
        // TODO(developer): add long running task here.
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
        //이 메서드는 호출 기회가 있기 전에도 작업 실행을 중지해야한다고 시스템에서 결정한 경우 호출 jobFinished(JobParameters, boolean)됩니다.
    }

    companion object {
        private const val TAG = "MyJobService"
    }
}