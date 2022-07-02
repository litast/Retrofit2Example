package lv.stauvere.lita.retrofit2jsonarray

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import lv.stauvere.lita.retrofit2jsonarray.databinding.ItemTodoBinding
import org.json.JSONArray


class CountryAdapter: RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback =  object : DiffUtil.ItemCallback<Countries>() {
        override fun areItemsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem[0] == newItem[0]
        }

        override fun areContentsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var countries: List<Countries>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = countries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return  CountryViewHolder(ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.apply {
            val listItem = countries[position]
            val element = JSONArray(listItem)
            for (i in 0 until countries.size-1) {
                tvID.text = element.getInt(0).toString()
                tvTitle.text = element.getString(1)
                tvCount.text = element.getInt(2).toString()
            }

        }
    }

}