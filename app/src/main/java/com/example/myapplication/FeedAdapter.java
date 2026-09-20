package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_COMPANY = 1;
    private static final int TYPE_FREELANCER = 2;

    private List<FeedItem> listaItens;

    public FeedAdapter(List<FeedItem> listaItens) {
        this.listaItens = listaItens;
    }

    @Override
    public int getItemViewType(int position) {
        return listaItens.get(position).getTipoCard();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_COMPANY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_company, parent, false);
            return new CompanyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_freelancer, parent, false);
            return new FreelancerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedItem item = listaItens.get(position);

        if (holder instanceof CompanyViewHolder) {
            CompanyViewHolder companyHolder = (CompanyViewHolder) holder;
            companyHolder.tvName.setText(item.getNomeEmpresa());
            companyHolder.tvTitle.setText(item.getTituloVaga());
            companyHolder.tvDesc.setText(item.getDescricaoVaga());
            companyHolder.tvDate.setText("📅 " + item.getData());
            companyHolder.tvLocation.setText("📍 " + item.getLocal());
            companyHolder.tvRate.setText("💰 " + item.getValorHora());
            companyHolder.tvRating.setText(item.getAvaliacao());
        } else if (holder instanceof FreelancerViewHolder) {
            FreelancerViewHolder freelaHolder = (FreelancerViewHolder) holder;
            freelaHolder.tvName.setText(item.getNomeFreelancer());
            freelaHolder.tvRole.setText(item.getCargo());
            freelaHolder.tvBio.setText(item.getBio());
            freelaHolder.tvRating.setText(item.getAvaliacao());
        }
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    // ViewHolder para Card Empresa
    static class CompanyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTitle, tvDesc, tvDate, tvLocation, tvRate, tvRating;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCompanyName);
            tvTitle = itemView.findViewById(R.id.tvJobTitle);
            tvDesc = itemView.findViewById(R.id.tvJobDescription);
            tvDate = itemView.findViewById(R.id.tvJobDate);
            tvLocation = itemView.findViewById(R.id.tvJobLocation);
            tvRate = itemView.findViewById(R.id.tvJobRate);
            tvRating = itemView.findViewById(R.id.tvCompanyRating);
        }
    }

    // ViewHolder para Card Freelancer
    static class FreelancerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRole, tvBio, tvRating;

        public FreelancerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFreelancerName);
            tvRole = itemView.findViewById(R.id.tvFreelancerRole);
            tvBio = itemView.findViewById(R.id.tvFreelancerBio);
            tvRating = itemView.findViewById(R.id.tvFreelancerRating);
        }
    }
}