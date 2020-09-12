package com.objectfanatics.chrono0018.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.objectfanatics.chrono0018.R
import com.objectfanatics.chrono0018.databinding.CustomViewLayoutBinding
import com.objectfanatics.commons.android.view.getNullableIntegerAttr

class CustomView : FrameLayout {
    constructor(context: Context) : super(context) { init(context) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init(context, attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init(context, attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) { init(context, attrs) }

    private val numberValue: MutableLiveData<Int?> = MutableLiveData()

    private fun init(context: Context, attrs: AttributeSet? = null) {
        when (attrs) {
            null -> initWithoutAttr(context)
            else -> initWithAttr(context, attrs)
        }
        CustomViewLayoutBinding.inflate(LayoutInflater.from(context), this, true).let { binding ->
            // FIXME: ちゃんと整理しましょう。
            binding.layoutModel = object : LayoutModel {
                override val number: LiveData<String?> = Transformations.map(numberValue) { numberValue ->
                    numberValue?.toString()
                }
            }
            // FIXME: ここは常にonになるやつを返すべき。
            binding.lifecycleOwner = context as AppCompatActivity
        }
    }

    // FIXME: LiveData への delegate 用ライブラリを作るべき。
    var number: Int?
        get() = numberValue.value
        set(value) {
            numberValue.postValue(value)
        }

    private fun initWithoutAttr(context: Context) {
        // do nothing
    }

    private fun initWithAttr(context: Context, attrs: AttributeSet) {
        number = getNullableIntegerAttr(attrs, R.styleable.CustomView, R.styleable.CustomView_number)
    }
}