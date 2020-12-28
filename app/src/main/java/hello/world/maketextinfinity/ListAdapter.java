package hello.world.maketextinfinity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    public ArrayList<Model> list = new ArrayList<>();
    private ArrayList<Model> arrayList = list;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);

            TextView mText = (TextView)view.findViewById(R.id.item_text);
            TextView mNum = (TextView)view.findViewById(R.id.item_num);

            holder.textView = mText;
            holder.numView = mNum;

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Model model = arrayList.get(i);
        holder.textView.setText(model.getText());
        holder.numView.setText(String.valueOf(model.getNum()));

        return view;
    }

    public void addItem(String text, int num){
        Model model = new Model();

        model.setText(text);
        model.setNum(num);

        list.add(model);
    }

    static class ViewHolder{
        TextView textView, numView;
    }
}
