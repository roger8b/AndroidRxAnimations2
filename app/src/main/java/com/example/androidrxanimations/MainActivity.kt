package com.example.androidrxanimations

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.BounceInterpolator
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.CompletableSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_start.setOnClickListener {
            resetCounter()
            val disposable = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .take(31)
                    .map {
                        updateCounter(it)
                    }
                    .subscribe()

            compositeDisposable.add(disposable)
        }

    }

    private fun resetCounter() {
        tv_counter.text = "0"
    }

    private fun updateCounter(it: Long) {
        tv_counter.scaleUp(400L)
                .andThen(Completable.fromCallable {
                    updateCounterView(it)
                }).andThen(tv_counter.scaleDown(400L))
                .subscribe()
    }

    private fun updateCounterView(it: Long) {
        tv_counter.text = it.toString()
    }

    fun View.fadeIn(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .alpha(1f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }

    fun View.fadeOut(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .alpha(0f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }

    fun View.translateUp(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .setInterpolator(BounceInterpolator())
                    .translationY(5f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }

    fun View.translateDown(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .setInterpolator(BounceInterpolator())
                    .translationY(0f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }

    fun View.scaleUp(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .setInterpolator(BounceInterpolator())
                    .scaleX(1.5f)
                    .scaleY(1.5f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }

    fun View.scaleDown(duration: Long) : Completable {
        val animationSubject = CompletableSubject.create()

        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                    .setDuration(duration)
                    .setInterpolator(BounceInterpolator())
                    .scaleX(1f)
                    .scaleY(1f)
                    .withEndAction{
                        animationSubject.onComplete()
                    }
        }
    }





}
