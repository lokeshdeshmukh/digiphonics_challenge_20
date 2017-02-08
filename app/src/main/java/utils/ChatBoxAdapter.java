package utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.ChatBox;
import com.example.android.materialdesigncodelab.R;

import java.util.ArrayList;

import structures.Master_Message1;

import static com.example.android.materialdesigncodelab.ChatBox.mynum;

/**
 * Created by Timmy on 2/8/17.
 */

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder> {

    int self = 51;
    private Context context;
    private ArrayList<Master_Message1> master_message1s = new ArrayList<>();


    public ChatBoxAdapter(Context context, ArrayList<Master_Message1> master_message1s){
        this.master_message1s=master_message1s;
        this.context = context;
    }


    @Override
    public ChatBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == self){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_message,parent,false);
            /*CardView.LayoutParams layoutParams = new CardView.LayoutParams(view.getWidth()-80,CardView.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);*/

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message,parent,false);
            /*view.setLayoutParams(new CardView.LayoutParams(view.getWidth()-80,CardView.LayoutParams.WRAP_CONTENT));
*/
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatBoxAdapter.ViewHolder holder, int position) {
        Master_Message1 master_message1 = master_message1s.get(position);
        if(master_message1.getMESSAGE()!=null){
            holder.selfText.setText(master_message1.getMESSAGE());
        }


    }

    @Override
    public int getItemViewType(int position) {
        Master_Message1 master_message1 = master_message1s.get(position);
        if(master_message1.getSENDER().equalsIgnoreCase(mynum)){
            return self;
        }
        return position;
    }


    @Override
    public int getItemCount() {
        return master_message1s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView selfText;
        public ViewHolder(View itemView) {
            super(itemView);
            selfText = (TextView)itemView.findViewById(R.id.selfText);

        }
    }
}
