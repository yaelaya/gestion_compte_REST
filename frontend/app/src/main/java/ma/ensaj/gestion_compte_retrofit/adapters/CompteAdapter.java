package ma.ensaj.gestion_compte_retrofit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.ensaj.gestion_compte_retrofit.MainActivity;
import ma.ensaj.gestion_compte_retrofit.R;
import ma.ensaj.gestion_compte_retrofit.models.Compte;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.ViewHolder> {

    private List<Compte> comptes;
    private Context context;

    public CompteAdapter(List<Compte> comptes, Context context) {
        this.comptes = comptes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        holder.accountId.setText(String.valueOf(compte.getId()));
        holder.accountBalance.setText(String.valueOf(compte.getSolde()));
        holder.accountCreationDate.setText(compte.getDateCreation());
        holder.accountType.setText(compte.getType());

        // Modifier un compte
        holder.updateAccountButton.setOnClickListener(v -> {
            compte.setSolde(compte.getSolde() + 100);  // Ajouter 100 au solde pour cet exemple
            ((MainActivity) context).updateCompte(compte.getId(), compte);
        });

        // Supprimer un compte
        holder.deleteAccountButton.setOnClickListener(v -> {
            ((MainActivity) context).deleteCompte(compte.getId());
        });
    }

    @Override
    public int getItemCount() {
        return comptes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView accountId, accountBalance, accountCreationDate, accountType;
        Button updateAccountButton, deleteAccountButton;

        public ViewHolder(View itemView) {
            super(itemView);
            accountId = itemView.findViewById(R.id.accountId);
            accountBalance = itemView.findViewById(R.id.accountBalance);
            accountCreationDate = itemView.findViewById(R.id.accountCreationDate);
            accountType = itemView.findViewById(R.id.accountType);
            updateAccountButton = itemView.findViewById(R.id.updateAccountButton);
            deleteAccountButton = itemView.findViewById(R.id.deleteAccountButton);
        }
    }
}
