package com.d2k.appictask.ui.main

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.d2k.appictask.R
import com.d2k.appictask.databinding.ActivityMainBinding
import com.d2k.appictask.databinding.BottomSheetFilterBinding
import com.d2k.appictask.databinding.BottomSheetListBinding
import com.d2k.appictask.ui.main.adapter.FilterListAdapter
import com.d2k.appictask.ui.main.model.FilterJson
import com.d2k.appictask.ui.main.model.FilterListModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    val accountList = arrayListOf<FilterListModel>()
    val brandList = arrayListOf<FilterListModel>()
    val locationList = arrayListOf<FilterListModel>()
    lateinit var bottomSheetFilterBinding: BottomSheetFilterBinding
    var brandSize: Int = 0
    var locationSize: Int = 0
    lateinit var filterData: FilterJson
    lateinit var filterListAdapter: FilterListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding =
            DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        openBottomSheetDialog()
        try {
            val obj = loadJSONFromAsset()?.let { JSONObject(it) }
            filterData =
                Gson().fromJson(obj?.get("filterData").toString(), FilterJson::class.java)
            filterData.get(0).hierarchy?.forEach {
                accountList.add(FilterListModel(it?.accountNumber ?: "", false))
            }
            selectAccountList(accountList)
        } catch (ex: Exception) {

        }
        activityMainBinding.tvText.setOnClickListener {
            openBottomSheetDialog()
            selectAccountList(accountList)
        }
    }

    private fun selectAccountList(accountList: List<FilterListModel>) {
        brandList.clear()
        locationList.clear()
        brandSize = 0
        locationSize = 0
        filterData.get(0).hierarchy?.forEach {
            accountList.forEach { accountList ->
                if (accountList.name.equals(it?.accountNumber) && accountList.isSelected) {
                    it?.brandNameList?.forEach {
                        brandList.add(FilterListModel(it?.brandName.toString(), true))
                        brandSize++
                        it?.locationNameList?.forEach {
                            locationList.add(FilterListModel(it?.locationName.toString(), true))
                            locationSize++
                        }
                    }
                } /*else {
                    it?.brandNameList?.forEach {
                        brandList.add(FilterListModel(it?.brandName.toString(), false))
                        it?.locationNameList?.forEach {
                            locationList.add(FilterListModel(it?.locationName.toString(), false))
                        }
                    }
                }*/

            }
        }
        setData()
    }

    private fun setData() {
        bottomSheetFilterBinding.btnAccNo.text =
            getString(R.string.select_account_number) + " " + accountList.size
        bottomSheetFilterBinding.btnBrand.text =
            getString(R.string.select_brand) + " " + brandList.size
        bottomSheetFilterBinding.btnLocation.text =
            getString(R.string.select_location) + " " + locationList.size
    }

    private fun selectBrandList(brandList: List<FilterListModel>) {
        locationSize = 0
        locationList.clear()
        brandList.forEach { brand ->
            filterData.get(0).hierarchy?.forEach {
                it?.brandNameList?.forEach {
                    if (it?.brandName.equals(brand.name) && brand.isSelected) {
                        it?.locationNameList?.forEach {
                            locationList.add(FilterListModel(it?.locationName.toString(), true))
                            locationSize++
                        }
                    } /*else {
                        it?.locationNameList?.forEach {
                            locationList.add(FilterListModel(it?.locationName.toString(), false))
                        }
                    }*/
                }
            }
        }
        setData()
    }

    private fun openBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetFilterBinding =
            BottomSheetFilterBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetFilterBinding.root)

        bottomSheetFilterBinding.btnAccNo.setOnClickListener {
            openBottomSheetListDialog(accountList, "accounts")
        }
        bottomSheetFilterBinding.btnBrand.setOnClickListener {
            openBottomSheetListDialog(brandList, "brands")
        }
        bottomSheetFilterBinding.btnLocation.setOnClickListener {
            openBottomSheetListDialog(locationList, "locations")
        }


        bottomSheetDialog.show()
    }

    private fun openBottomSheetListDialog(accountList: ArrayList<FilterListModel>, from: String) {
        val bottomSheetDialog2 = BottomSheetDialog(this)
        val bottomSheetListBinding =
            BottomSheetListBinding.inflate(layoutInflater, null, false)
        bottomSheetListBinding.tvTitle.text = "Search $from"
        bottomSheetDialog2.setContentView(bottomSheetListBinding.root)
        bottomSheetListBinding.recyclerView2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        filterListAdapter = FilterListAdapter(this, accountList)
        bottomSheetListBinding.recyclerView2.adapter = filterListAdapter
        bottomSheetListBinding.btnAddFilter.setOnClickListener {
            bottomSheetDialog2.dismiss()
            if (from.equals("accounts"))
                filterListAdapter.getList()?.let { it1 -> selectAccountList(it1) }
            else if (from.equals("brands"))
                filterListAdapter.getList()?.let {
                    selectBrandList(it)
                }
        }

        bottomSheetListBinding.svList.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterListAdapter.filter.filter(newText)
                return false
            }
        })
        bottomSheetDialog2.show()
        bottomSheetListBinding.btnClose.setOnClickListener {
            bottomSheetDialog2.dismiss()
        }
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = getAssets().open("TestJSON.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}