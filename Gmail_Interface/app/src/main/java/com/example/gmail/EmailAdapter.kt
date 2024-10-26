import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.gmail.R

class EmailAdapter(private val context: Context, private val emails: List<Email>) : BaseAdapter() {

    override fun getCount(): Int {
        return emails.size
    }

    override fun getItem(position: Int): Any {
        return emails[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val email = getItem(position) as Email
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.icon.setImageResource(email.icon)
        viewHolder.sender.text = email.sender
        viewHolder.subject.text = email.subject
        viewHolder.time.text = email.time
        viewHolder.content.text = email.content

        return view
    }

    private class ViewHolder(view: View) {
        val content: TextView = view.findViewById(R.id.content)
        val icon: ImageView = view.findViewById(R.id.icon)
        val sender: TextView = view.findViewById(R.id.sender)
        val subject: TextView = view.findViewById(R.id.subject)
        val time: TextView = view.findViewById(R.id.time)
    }
}
