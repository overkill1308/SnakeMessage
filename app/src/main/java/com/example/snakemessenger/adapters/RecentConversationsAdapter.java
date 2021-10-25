package com.example.snakemessenger.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snakemessenger.databinding.ItemUserRecentConversionBinding;
import com.example.snakemessenger.listeners.ConversionListener;
import com.example.snakemessenger.models.ChatMessage;
import com.example.snakemessenger.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener =  conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(ItemUserRecentConversionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{

        ItemUserRecentConversionBinding binding;
        ConversionViewHolder(ItemUserRecentConversionBinding userRecentConversionBinding){
            super(userRecentConversionBinding.getRoot());
            binding = userRecentConversionBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.imgProfile.setImageBitmap(getRecentConversionImage(chatMessage.conversionImage));
            binding.tvName.setText(chatMessage.conversionName);
            binding.tvRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = new User();
                    user.id = chatMessage.conversionId;
                    user.name = chatMessage.conversionName;
                    user.image = chatMessage.conversionImage;
                    conversionListener.onConversionClicked(user);
                }
            });
        }

    }

    private Bitmap getRecentConversionImage(String encodeImage){
        byte[] bytes = Base64.decode(encodeImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
