package com.example.weatherdemo.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherdemo.Bean.WeatherBean;
import com.example.weatherdemo.BroadCastManager;
import com.example.weatherdemo.DBHelper;
import com.example.weatherdemo.MainActivity;
import com.example.weatherdemo.MyAdapter;
import com.example.weatherdemo.R;
import com.example.weatherdemo.Utils.JsonUtils;
import com.example.weatherdemo.entities.cityItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingsFragment extends Fragment {
    //  数据 省--城市--城市id
    private String[] p_data = {"直辖市", "特别行政区", "黑龙江", "吉林", "辽宁", "内蒙古",
            "河北", "河南", "山东", "山西", "江苏", "安徽", "陕西", "宁夏", "甘肃", "青海",
            "湖北", "湖南", "浙江", "江西", "福建", "贵州", "四川", "广东", "广西",
            "云南", "海南", "新疆", "西藏", "台湾"};

    private String[][] c_data = {{"北京", "上海", "天津", "重庆"},
            {"香港", "澳门"},
            {"哈尔滨", "齐齐哈尔", "牡丹江", "大庆", "伊春", "双鸭山", "鹤岗", "鸡西", "佳木斯", "七台河", "黑河",
                    "绥化", "大兴安岭"},
            {"长春", "延吉", "吉林", "白山", "白城", "四平", "松原", "辽源", "大安", "通化"},
            {"沈阳", "大连", "葫芦岛", "盘锦", "本溪", "抚顺", "铁岭", "辽阳", "营口", "阜新", "朝阳", "锦州",
                    "丹东", "鞍山"},
            {"呼和浩特", "呼伦贝尔", "锡林浩特", "包头", "赤峰", "海拉尔", "乌海", "鄂尔多斯", "通辽"},
            {"石家庄", "唐山", "张家口", "廊坊", "邢台", "邯郸", "沧州", "衡水", "承德", "保定", "秦皇岛"},
            {"郑州", "开封", "洛阳", "平顶山", "焦作", "鹤壁", "新乡", "安阳", "濮阳", "许昌", "漯河", "三门峡",
                    "南阳", "商丘", "信阳", "周口", "驻马店"},
            {"济南", "青岛", "淄博", "威海", "曲阜", "临沂", "烟台", "枣庄", "聊城", "济宁", "菏泽", "泰安", "日照",
                    "东营", "德州", "滨州", "莱芜", "潍坊"},
            {"太原", "阳泉", "晋城", "晋中", "临汾", "运城", "长治", "朔州", "忻州", "大同", "吕梁"},
            {"南京", "苏州", "昆山", "南通", "太仓", "吴县", "徐州", "宜兴", "镇江", "淮安", "常熟", "盐城", "泰州",
                    "无锡", "连云港", "扬州", "常州", "宿迁"},
            {"合肥", "巢湖", "蚌埠", "安庆", "六安", "滁州", "马鞍山", "阜阳", "宣城", "铜陵", "淮北", "芜湖",
                    "毫州", "宿州", "淮南", "池州"},
            {"西安", "韩城", "安康", "汉中", "宝鸡", "咸阳", "榆林", "渭南", "商洛", "铜川", "延安"},
            {"银川", "固原", "中卫", "石嘴山", "吴忠"},
            {"兰州", "白银", "庆阳", "酒泉", "天水", "武威", "张掖", "甘南", "临夏", "平凉", "定西", "金昌"},
            {"西宁", "海北", "海西", "黄南", "果洛", "玉树", "海东", "海南"},
            {"武汉", "宜昌", "黄冈", "恩施", "荆州", "神农架", "十堰", "咸宁", "襄阳", "孝感", "随州", "黄石",
                    "荆门", "鄂州"},
            {"长沙", "邵阳", "常德", "郴州", "吉首", "株洲", "娄底", "湘潭", "益阳", "永州", "岳阳", "衡阳", "怀化",
                    "韶山", "张家界"},
            {"杭州", "湖州", "金华", "宁波", "丽水", "绍兴", "衢州", "嘉兴", "台州", "舟山", "温州"},
            {"南昌", "萍乡", "九江", "上饶", "抚州", "吉安", "鹰潭", "宜春", "新余", "景德镇", "赣州"},
            {"福州", "厦门", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "漳州"},
            {"贵阳", "安顺", "赤水", "遵义", "铜仁", "六盘水", "毕节", "凯里", "都匀"},
            {"成都", "泸州", "内江", "凉山", "阿坝", "巴中", "广元", "乐山", "绵阳", "德阳", "攀枝花", "雅安",
                    "宜宾", "自贡", "甘孜州", "达州", "资阳", "广安", "遂宁", "眉山", "南充"},
            {"广州", "深圳", "潮州", "韶关", "湛江", "惠州", "清远", "东莞", "江门", "茂名", "肇庆", "汕尾", "河源",
                    "揭阳", "梅州", "中山", "德庆", "阳江", "云浮", "珠海", "汕头", "佛山"},
            {"南宁", "桂林", "阳朔", "柳州", "梧州", "玉林", "桂平", "贺州", "钦州", "贵港", "防城港", "百色",
                    "北海", "河池", "来宾", "崇左"},
            {"昆明", "保山", "楚雄", "德宏", "红河", "临沧", "怒江", "曲靖", "思茅", "文山", "玉溪", "昭通", "丽江",
                    "大理"},
            {"海口", "三亚", "儋州", "琼山", "通什", "文昌"},
            {"乌鲁木齐", "阿勒泰", "阿克苏", "昌吉", "哈密", "和田", "喀什", "克拉玛依", "石河子", "塔城",
                    "库尔勒", "吐鲁番", "伊宁"},
            {"拉萨", "阿里", "昌都", "那曲", "日喀则", "山南", "林芝"},
            {"台北", "高雄"}};

    private String[][] cid_data = {{"101010100", "101020100", "101030100", "101040100"},
            {"101320101", "101330101"},
            {"101050101", "101050201", "101050301", "101050901", "101050801", "101051301", "101051201",
                    "101051101", "101050401", "101051002", "101050601", "101050501", "101050701"},
            {"101060101", "101060301", "101060201", "101060901", "101060601", "101060401", "101060801",
                    "101060701", "101060603", "101060501"},
            {"101070101", "101070201", "101071401", "101071301", "101070501", "101070401", "101071101",
                    "101071001", "101070801", "101070901", "101071201", "101070701", "101070601",
                    "101070301"},
            {"101080101", "101081001", "101080901", "101080201", "101080601", "101081001", "101080301",
                    "101080701", "101080501"},
            {"101090101", "101090501", "101090301", "101090601", "101090901", "101091001", "101090701",
                    "101090801", "101090402", "101090201", "101091101"},
            {"101180101", "101180801", "101180901", "101180501", "101181101", "101181201", "101180301",
                    "101180201", "101181301", "101180401", "101181501", "101181701", "101180701",
                    "101181001", "101180601", "101181401", "101181601"},
            {"101120101", "101120201", "101120301", "101121301", "101120710", "101120901", "101120501",
                    "101121401", "101121701", "101120701", "101121001", "101120801", "101121501",
                    "101121201", "101120401", "101121101", "101121601", "101120601"},
            {"101100101", "101100301", "101100601", "101100401", "101100701", "101100801", "101100501",
                    "101100901", "101101001", "101100201", "101101101"},
            {"101190101", "101190401", "101190404", "101190501", "101190408", "101190406", "101190801",
                    "101190203", "101190301", "101190901", "101190402", "101190701", "101191201",
                    "101190201", "101191001", "101190601", "101191101", "101191301"},
            {"101220101", "101221601", "101220201", "101220601", "101221501", "101221101", "101220501",
                    "101220801", "101221401", "101221301", "101221201", "101220301", "101220901",
                    "101220701", "101220401", "101221701"},
            {"101110101", "101110510", "101110701", "101110801", "101110901", "101110200", "101110401",
                    "101110501", "101110601", "101111001", "101110300"},
            {"101170101", "101170401", "101170501", "101170201", "101170301"},
            {"101160101", "101161301", "101160401", "101160801", "101160901", "101160501", "101160701",
                    "101050204", "101161101", "101160301", "101160201", "101160601"},
            {"101150101", "101150801", "101150701", "101150301", "101150501", "101150601", "101150201",
                    "101150401"},
            {"101200101", "101200901", "101200501", "101201001", "101200801", "101201201", "101201101",
                    "101200701", "101200201", "101200401", "101201301", "101200601", "101201401",
                    "101200301"},
            {"101250101", "101250901", "101250601", "101250501", "101251501", "101250301", "101250801",
                    "101250201", "101250701", "101251401", "101251001", "101250401", "101251201",
                    "101250202", "101251101"},
            {"101210101", "101210201", "101210901", "101210401", "101210801", "101210501", "101211001",
                    "101210301", "101210601", "101211101", "101210701"},
            {"101240101", "101240901", "101240201", "101240301", "101240401", "101240601", "101241101",
                    "101240501", "101241001", "101240801", "101240701"},
            {"101230101", "101230201", "101230701", "101230901", "101230301", "101230401", "101230501",
                    "101230801", "101230601"},
            {"101260101", "101260301", "101260208", "101260201", "101260601", "101260801", "101260701",
                    "101260501", "101260401"},
            {"101270101", "101271001", "101271201", "101271601", "101271901", "101270901", "101272101",
                    "101271401", "101270401", "101272001", "101270201", "101271701", "101271101",
                    "101270301", "101271801", "101270601", "101271301", "101270801", "101270701",
                    "101271501", "101270501"},
            {"101280101", "101280601", "101281501", "101280201", "101281001", "101280301", "101281301",
                    "101281601", "101281101", "101282001", "101280901", "101282101", "101281201",
                    "101281901", "101280401", "101281701", "101280905", "101281801", "101281401",
                    "101280701", "101280501", "101280800"},
            {"101300101", "101300501", "101300510", "101300301", "101300601", "101300901",
                    "101300802", "101300701", "101301101", "101300801", "101301401", "101301001",
                    "101301301", "101301201", "101300401", "101300201"},
            {"101290101", "101290501", "101290801", "101291501", "101290301", "101291101",
                    "101291201", "101290401", "101290901", "101290601", "101290701", "101291001",
                    "101291401", "101290201"},
            {"101310101", "101310201", "101310205", "101310102", "101310222", "101310212"},
            {"101130101", "101131401", "101130801", "101130401", "101131201", "101131301", "101130901",
                    "101130201", "101130301", "101131101", "101130601", "101130501", "101131001"},
            {"101140101", "101140701", "101140501", "101140601", "101140201", "101140301", "101140401"},
            {"101340102", "101340201"}};


    private static final String TAG = "SettingsFragment";

    private EditText et_search;
    private Spinner sp_city, sp_province;
    private Button btn_search, btn_insert, btn_delete;
    private ListView lv_city;

    private ArrayAdapter<String> prvAdapter, cityAdapter;
    private List<String> cities, provinces;
    private List<String> provinceList;

    private DBHelper dbHelper = null;
    private SQLiteDatabase db = null;

    private boolean isGetData = false;
    private WeatherBean weatherBean = null;

    private MyAdapter myAdapter = null;
    List<cityItem> list = new ArrayList<cityItem>();

    private String citykey = "00000000";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_settings_page, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDb();
        initView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String cityKey = ((MainActivity) context).toCityKey();
        Log.d(TAG, cityKey);
    }


    @Override
    public void onResume() {
        if (!isGetData) {

            isGetData = true;
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    private void initView() {
        et_search = getActivity().findViewById(R.id.et_search);

        sp_city = getActivity().findViewById(R.id.sp_city);
        sp_province = getActivity().findViewById(R.id.sp_province);

        lv_city = getActivity().findViewById(R.id.lv_city);

        btn_search = getActivity().findViewById(R.id.btn_search);
        btn_delete = getActivity().findViewById(R.id.btn_delete);
        btn_insert = getActivity().findViewById(R.id.btn_insert);

        //创建省份数据
        getProvince();

        //数据源与sp_province的适配器
        prvAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                provinceList);

        //将sp_province和prvAdapter绑定
        sp_province.setAdapter(prvAdapter);
        //设置事件
        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //数据源与sp_city的适配器
                List<String> citys = getCitys(position);
                cityAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        citys);
                sp_city.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //lv_city 添加适配器
        getDBData();
        myAdapter = new MyAdapter(getContext(), list);
        //ListView item 中的删除按钮的点击事件
        lv_city.setAdapter(myAdapter);
        //添加 按钮事件
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String province = et_search.getText().toString();
                if (province.equals("请输入搜索的省份或城市"))
                    Toast.makeText(getContext(), "请输入搜索的省份或城市", Toast.LENGTH_SHORT).show();
                else {
                    int pos = findInP_data(province);
                    if (pos != -1) {
                        sp_province.setSelection(pos);
                    } else {
                        int[] pos2 = findInC_data(province);
                        if (pos2[0] != -1 && pos2[1] != -1) {
                            sp_province.setSelection(pos2[0]);
                            sp_city.setSelection(pos2[1]);
                        } else {
                            Toast.makeText(getContext(), "请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btn_insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String p_name = "", c_name = "", c_key = "";
                p_name = sp_province.getSelectedItem().toString();
                c_name = sp_city.getSelectedItem().toString();
                c_key = findCityKey(p_name, c_name);
                if (!c_key.equals("0")) {
                    Cursor c = db.rawQuery("SELECT * FROM citylist where citykey=? ", new String[]{c_key});
                    if (c.getCount() == 0) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("citykey", c_key);
                        contentValues.put("city", c_name);
                        contentValues.put("province", p_name);
                        db.insert("citylist", null, contentValues);
                        Log.d(TAG, "list添加数据 " + p_name + " " + c_name);
                        getDBData();
                        lv_city.setAdapter(myAdapter);
                        Toast.makeText(getContext(), "已添加！", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "已添加！", Toast.LENGTH_SHORT).show();
                    c.close();
                } else Toast.makeText(getContext(), "没有该城市！", Toast.LENGTH_SHORT).show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String p_name = "", c_name = "", c_key = "";
                p_name = sp_province.getSelectedItem().toString();
                c_name = sp_city.getSelectedItem().toString();
                c_key = findCityKey(p_name, c_name);

                if (!c_key.equals("0")) {
                    Cursor c = db.rawQuery("SELECT * FROM citylist where citykey=? ", new String[]{c_key});
                    if (c.getCount() != 0) {
                        db.delete("citylist", "citykey=?", new String[]{c_key});
                        listDelete(c_name);
                        lv_city.setAdapter(myAdapter);
                        Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "已删除！", Toast.LENGTH_SHORT).show();
                    c.close();
                } else Toast.makeText(getContext(), "没有该城市！", Toast.LENGTH_SHORT).show();
            }
        });
        //lv_city item的点击事件
        myAdapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                Cursor c = db.rawQuery("SELECT * FROM citylist where city=? ", new String[]{list.get(i).getCity()});
                if (c.getCount() != 0) {
                    Log.d(TAG, "将被删除的城市 " + i + "   " + list.get(i).getCity());
                    db.delete("citylist", "city=?", new String[]{list.get(i).getCity()});
                    listDelete(list.get(i).getCity());
                    lv_city.setAdapter(myAdapter);
                    Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "已删除！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myAdapter.setOnItemUpdateClickListener(new MyAdapter.onItemUpdateListener() {
            @Override
            public void onUpdateClick(int i) {
                Log.d(TAG, "你选择更新的城市数据是：" + list.get(i).getCity());
                int[] pos = findInC_data(list.get(i).getCity());
                citykey = cid_data[pos[0]][pos[1]];
                Log.d(TAG, "企图更新的城市代码是：" + citykey);
                //发送广播
                Intent intent=new Intent("cityKey");
                intent.putExtra("citykey",citykey);
                getActivity().sendBroadcast(intent);
            }
        });
    }

    public String getCityKey() {
        return citykey;
    }

    private void listDelete(String c_name) {
        Iterator<cityItem> itemIterator = list.iterator();
        while (itemIterator.hasNext()) {
            cityItem item = itemIterator.next();
            if (item.getCity().equals(c_name)) {
                Log.d(TAG, "listDelete删除的内容是：" + item.getCity() + " 按要求删除内容是：" + c_name);
                //这两个很重要
                itemIterator.remove();
                list.remove(item);
            }
        }
    }

    private List<cityItem> getDBData() {
        list.clear();
        cityItem item = null;
        Cursor c = db.query("citylist", null, null, null, null, null, null);
        while (c.moveToNext()) {
            item = new cityItem(c.getString(c.getColumnIndex("city")),
                    c.getString(c.getColumnIndex("province")));
            Log.d(TAG, "添入的数据是：" + item.getCity() + " " + item.getProvince());
            list.add(item);
        }
        return list;
    }

    private void initDb() {
        String sql = "create table if not exists citylist(" +
                "citykey varchar(20) primary key," +
                "city varchar(20)," +
                "province varchar(20));";
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    private String findCityKey(String p_name, String c_name) {
        for (int i = 0; i < p_data.length; i++) {
            if (p_data[i].equals(p_name)) {
                for (int j = 0; j < c_data[i].length; j++) {
                    if (c_data[i][j].equals(c_name)) return cid_data[i][j];
                }
            }
        }
        return "0";
    }

    private void getProvince() {
        provinceList = new ArrayList<String>();
        for (String prov : p_data) {
            provinceList.add(prov);
        }
    }

    private List<String> getCitys(int position) {
        List<String> citys = new ArrayList<String>(); //傻子吧，怎么能写null呢？？？要创建空间啊，后面要添加数据的
        for (int i = 0; i < c_data[position].length; i++) {
            citys.add(c_data[position][i]);
        }
        return citys;
    }

    private int findInP_data(String province) {
        for (int i = 0; i < p_data.length; i++) {
            if (p_data[i].equals(province)) return i;
        }
        return -1;
    }

    private int[] findInC_data(String province) {
        int[] key = new int[]{-1, -1};
        for (int i = 0; i < p_data.length; i++) {
            key[0] = i;
            for (int j = 0; j < c_data[i].length; j++) {
                if (c_data[i][j].equals(province)) {
                    key[1] = j;
                    return key;
                }
            }
        }
        return new int[]{-1, -1};
    }
}
