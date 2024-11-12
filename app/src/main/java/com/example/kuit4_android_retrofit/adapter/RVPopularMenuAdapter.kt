
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuit4_android_retrofit.data.MenuCategoryData
import com.example.kuit4_android_retrofit.databinding.ItemCategoryMenuBinding

class RVPopularMenuAdapter(
    private val menuList: List<MenuCategoryData>,
) : RecyclerView.Adapter<RVPopularMenuAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemCategoryMenuBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuCategoryData) {
            binding.ivMainCategory.setImageResource(item.menuImg)
            binding.tvMainCategoryName.text = item.menuName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemCategoryMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
