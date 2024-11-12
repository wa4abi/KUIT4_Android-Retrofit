
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuit4_android_retrofit.data.MenuCategoryData
import com.example.kuit4_android_retrofit.databinding.ItemMenuCategoryBinding

class RVPopularMenuAdapter(
    private val menuList: List<MenuCategoryData>,
) : RecyclerView.Adapter<RVPopularMenuAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemMenuCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuCategoryData) {
            binding.ivMenuCategoryImg.setImageResource(item.menuCategoryImg)
            binding.tvMenuCategoryName.text = item.menuCategoryName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemMenuCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int = menuList.size
}
