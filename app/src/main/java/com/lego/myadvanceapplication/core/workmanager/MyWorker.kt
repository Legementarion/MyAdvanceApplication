package com.lego.myadvanceapplication.core.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params)  {

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }

}