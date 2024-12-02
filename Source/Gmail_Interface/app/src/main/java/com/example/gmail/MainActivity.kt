package com.example.gmail

import Email
import EmailAdapter
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emails = ArrayList<Email>()
        emails.add(Email(R.drawable.khaile,"Khai Le Quang", "Nộp báo cáo tính toán khoa học", "9:15 PM", "Xin chào, tôi tên là Lê Quang Khải. XIn vui lòng nộp báo cáo TTKH của bạn vào tối nay."))
        emails.add(Email(R.drawable.ic_email1, "Bình Gold", "Nghe nhạc không", "10:00 AM", "Bản hit mới toanh đã sẵn sàng ra mắt! \uD83D\uDD25\n" +
                "Sau thời gian dài ấp ủ và sáng tạo, Bình Gold chính thức quay trở lại với siêu phẩm mới \"Đừng Chơi Với Lửa\""))
        emails.add(Email(R.drawable.ic_emailt, "Tran Binh Minh", "Đơn xin nghỉ học", "19:00 PM", "Kính gửi thầy Trịnh Hữu An! Tên của em là Trần Bình Minh."))
        emails.add(Email(R.drawable.ic_emailh, "Harry Kane", "the Ultimate Football Match!", "8:20 AM", "Get ready for an exciting football showdown this weekend! Be there to witness top-tier action and unforgettable moments on the field."))
        emails.add(Email(R.drawable.ic_email1, "Bobby", "Friendly Check-In", "1:00 AM", "Hey there! It's been a while. How are you doing these days? Would love to catch up soon!"))
        emails.add(Email(R.drawable.ic_emailn, "Nelson Mandela", "Exclusive Invitation: Workshop on Personal Growth", "6:21 PM", "Dear valued participant, we are excited to invite you to our upcoming workshop on personal growth and development. Join us to unlock your full potential and achieve your goals!"))
        emails.add(Email(R.drawable.apple,"Apple", "Giới thiệu Apple Watch Series hoàn toàn mới", "7:09 AM", "Series 10 có màn hình lớn nhất và thiết kế mỏng nhất từ trước đến nay"))
        emails.add(Email(R.drawable.phamlong,"Pham Duc Long", "LET ME TAKE CARE OF YOU", "1:34 PM", "OH NAGI-CHAMA, CAN I ASK YOU A FAVORPLEASE LET ME HAVE A TASTE"))
        emails.add(Email(R.drawable.shopee,"Shopee", "Xin chào trinhan372, Shopee muốn lắng nghe ý kiến của bạn!", "1:34 PM", "Chào bạn trinhan372,\n" +
                "Ý kiến của bạn rất quan trọng.\n" +
                "Chia sẻ trải nghiệm gần đầy của bạn trên Shopee"))

        val listView: ListView = findViewById(R.id.listView)
        val emailAdapter = EmailAdapter(this, emails)
        listView.adapter = emailAdapter
    }
}
