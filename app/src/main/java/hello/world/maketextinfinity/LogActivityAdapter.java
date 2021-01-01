package hello.world.maketextinfinity;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LogActivityAdapter extends RecyclerView.Adapter<LogActivityAdapter.LogViewHolder> {
    private List<ItemEntity> mList = new ArrayList<>();
    private final ItemDatabase db;
    private int idx;
    private final Context context;
    ItemClickListener itemClickListener;
    private String countTime, finishCopy, finishDelete;

    public static class LogViewHolder extends RecyclerView.ViewHolder{
        protected TextView itemCount;
        protected TextView itemContents;
        protected TextView itemDelete;
        protected Button copyBtn;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemCount = itemView.findViewById(R.id.item_count);
            this.itemContents = itemView.findViewById(R.id.item_contents);
            this.itemDelete = itemView.findViewById(R.id.item_delete);
            this.copyBtn = itemView.findViewById(R.id.copy_btn);
        }
    }

    public LogActivityAdapter(ItemDatabase db, Context context){
        this.db = db;
        this.context = context;
    }

    // Create item
    public void setItem(List<ItemEntity> data){
        mList = data;
        notifyDataSetChanged();
    }

    // Update item
    public void updateItem(ItemEntity dictionaryEntity){
        new Thread(() -> {
            mList.get(idx).setCount(dictionaryEntity.getCount());
            mList.get(idx).setContents(dictionaryEntity.getContents());
            db.itemDao().update(mList.get(idx));
        }).start();
    }

    // Delete all items
    public void deleteAll(){
        new Thread(()->{
            mList.clear();
            db.itemDao().deleteAll();
        }).start();
    }

    // Remove contents is null
    public void deleteNull() {
        new Thread(()->{
            for(int i=0; i<mList.size(); i++){
                if(mList.get(i).getContents() == null){
                    mList.remove(i);
                }
            }
            db.itemDao().deleteNull();
        }).start();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        holder.itemCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        holder.itemContents.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        holder.itemCount.setText(String.valueOf(mList.get(position).getCount()));
        // Prevent string word wrap.
        holder.itemContents.setText(mList.get(position).getContents().replace(" ", "\u00A0"));

        holder.itemCount.setGravity(Gravity.LEFT);
        holder.itemContents.setGravity(Gravity.LEFT);
        holder.itemDelete.setGravity(Gravity.LEFT);

        setText(countTime, finishCopy, finishDelete);

        // Item click event.
        // Send list position and entity.
        final ItemEntity dictionary = mList.get(position);
        holder.itemView.setOnClickListener(v -> {
//            itemClickListener.OnItemClick(position, dictionary);
            idx = position;
        });

        // 리스트 삭제. (Delete)
        holder.itemDelete.setOnClickListener(v -> {
            // mList.remove(position); => 제대로 동작하지 않음
//                mList.remove(viewHolder.getAdapterPosition());
//                notifyDataSetChanged();
            new Thread(() -> {
                ItemEntity dict = mList.remove(holder.getAdapterPosition());
                db.itemDao().delete(dict);
            }).start();
            Toast.makeText(context, holder.itemContents.getText().toString()+" "+holder.itemCount.getText().toString()+" "+countTime+" "+finishDelete, Toast.LENGTH_SHORT).show();
        });

        holder.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.itemCount.getText().toString());
                String contents = holder.itemContents.getText().toString();
                String result = repeatText(contents, count);
                ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("copy_result", result);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(v.getContext(), contents+" "+count+" "+countTime+" "+finishCopy, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String repeatText(String text, int n){
        String result = text;
        for(int i=0; i<n-1; i++){
            result += text;
        }
        return result;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setText(String t1, String t2, String t3){
        t1 = context.getResources().getString(R.string.count_time);
        t2 = context.getResources().getString(R.string.finish_copy);
        t3 = context.getResources().getString(R.string.finish_delete);

        this.countTime = t1;
        this.finishCopy = t2;
        this.finishDelete = t3;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
