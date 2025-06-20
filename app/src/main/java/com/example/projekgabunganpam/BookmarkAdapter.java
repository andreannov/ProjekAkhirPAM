package com.example.projekgabunganpam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// [FILE BARU] Adapter untuk RecyclerView bookmark
public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<Bookmark> bookmarkList;
    private OnBookmarkClickListener listener;

    public interface OnBookmarkClickListener {
        void onBookmarkClick(Bookmark bookmark);
    }

    public BookmarkAdapter(List<Bookmark> bookmarkList, OnBookmarkClickListener listener) {
        this.bookmarkList = bookmarkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Bookmark currentBookmark = bookmarkList.get(position);
        holder.bind(currentBookmark, listener);
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvRoomType, tvNotes;
        ImageView ivRoomImage;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi semua view dari item_bookmark.xml
            tvRoomName = itemView.findViewById(R.id.tvRoomName); // Pastikan ID ini ada di item_bookmark.xml
            tvRoomType = itemView.findViewById(R.id.tvRoomType); // Pastikan ID ini ada di item_bookmark.xml
            tvNotes = itemView.findViewById(R.id.tvNotes);     // Pastikan ID ini ada di item_bookmark.xml
            ivRoomImage = itemView.findViewById(R.id.ivRoomImage); // Pastikan ID ini ada di item_bookmark.xml
        }

        public void bind(final Bookmark bookmark, final OnBookmarkClickListener listener) {
            tvRoomName.setText(bookmark.getRoomName());
            tvRoomType.setText("Jenis Ruang: " + bookmark.getRoomType());
            tvNotes.setText("Catatan: " + bookmark.getNotes());
            ivRoomImage.setImageResource(bookmark.getImageResId());

            // Tambahkan listener klik ke seluruh item view
            itemView.setOnClickListener(v -> listener.onBookmarkClick(bookmark));
        }
    }
}