package ma.ensaj.gestion_compte_retrofit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.ensaj.gestion_compte_retrofit.adapters.CompteAdapter;
import ma.ensaj.gestion_compte_retrofit.models.Compte;
import ma.ensaj.gestion_compte_retrofit.network.CompteApi;
import ma.ensaj.gestion_compte_retrofit.network.RetrofitClient;
import ma.ensaj.gestion_compte_retrofit.services.CompteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Tag pour les logs
    private RecyclerView recyclerView;
    private boolean isXmlFormat = false; // Par défaut, JSON est sélectionné

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        // Initialiser RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAccounts); // Mise à jour avec l'ID correct
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurer le Spinner
        Spinner spinnerFormat = findViewById(R.id.spinnerAccountType); // Mise à jour avec l'ID correct
        spinnerFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Vérification de la sélection
                if (position == 1) { // 1 pourrait correspondre à XML
                    isXmlFormat = true;
                    Log.d("Spinner", "Format sélectionné : XML");
                } else {
                    isXmlFormat = false;
                    Log.d("Spinner", "Format sélectionné : JSON");
                }
                fetchComptes(); // Charger les données après sélection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aucun élément sélectionné
                Log.d("Spinner", "Aucun format sélectionné");
            }
        });

        // Charger les données par défaut (avec JSON)
        fetchComptes();
    }

    private void fetchComptes() {
        String acceptHeader = isXmlFormat ? "application/xml" : "application/json";

        CompteApi api = CompteService.getInstance(isXmlFormat).create(CompteApi.class);

        api.getAllComptes(acceptHeader).enqueue(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
                if (response.body() != null) {
                    List<Compte> comptes = response.body();
                    recyclerView.setAdapter(new CompteAdapter(comptes, MainActivity.this));
                } else {
                    Log.e("fetchComptes", "Aucune donnée reçue");
                }
            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                Log.e("fetchComptes", "Erreur : " + t.getMessage());
            }
        });
    }

    public void updateCompte(Long id, Compte currentCompte) {
        // Créer un Dialog pour modifier le compte
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Mettre à jour le compte");

        // Créer un Layout pour la boîte de dialogue avec des EditTexts pour chaque champ
        View dialogView = getLayoutInflater().inflate(R.layout.form_compte, null);
        EditText editTextSolde = dialogView.findViewById(R.id.editTextAccountBalance); // Mise à jour avec l'ID correct
        EditText editTextDateCreation = dialogView.findViewById(R.id.editTextAccountCreationDate); // Mise à jour avec l'ID correct
        EditText editTextType = dialogView.findViewById(R.id.editTextAccountType); // Mise à jour avec l'ID correct

        // Remplir les EditTexts avec les valeurs actuelles du compte
        editTextSolde.setText(String.valueOf(currentCompte.getSolde()));
        editTextDateCreation.setText(currentCompte.getDateCreation());
        editTextType.setText(currentCompte.getType());

        builder.setView(dialogView)
                .setPositiveButton("Mettre à jour", (dialog, which) -> {
                    // Récupérer les valeurs saisies par l'utilisateur
                    double newSolde = Double.parseDouble(editTextSolde.getText().toString());
                    String newDateCreation = editTextDateCreation.getText().toString();
                    String newType = editTextType.getText().toString();

                    // Créer un objet Compte avec les nouvelles données
                    Compte updatedCompte = new Compte(id, newSolde, newDateCreation, newType);

                    // Appeler la méthode pour mettre à jour le compte via l'API
                    updateCompteInDatabase(id, updatedCompte);
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void updateCompteInDatabase(Long id, Compte updatedCompte) {
        CompteApi api = CompteService.getInstance(isXmlFormat).create(CompteApi.class);

        api.updateCompte(id, updatedCompte).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Compte mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    fetchComptes(); // Recharger les comptes après la mise à jour
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Log.e(TAG, "Erreur lors de la mise à jour du compte : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour supprimer un compte
    public void deleteCompte(Long id) {
        CompteApi api = CompteService.getInstance(isXmlFormat).create(CompteApi.class);
        api.deleteCompte(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Compte supprimé avec succès", Toast.LENGTH_SHORT).show();
                    fetchComptes(); // Recharger les comptes après la suppression
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Erreur lors de la suppression du compte : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAddAccountClick(View view) {
        // Créer un Dialog pour ajouter un nouveau compte
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Ajouter un nouveau compte");

        // Créer un Layout pour la boîte de dialogue avec des EditTexts pour chaque champ
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);
        EditText editTextBalance = dialogView.findViewById(R.id.editTextBalance);
        EditText editTextCreationDate = dialogView.findViewById(R.id.editTextCreationDate);
        EditText editTextAccountType = dialogView.findViewById(R.id.editTextAccountType);

        builder.setView(dialogView)
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    // Récupérer les valeurs saisies par l'utilisateur
                    double newSolde = Double.parseDouble(editTextBalance.getText().toString());
                    String newDateCreation = editTextCreationDate.getText().toString();
                    String newType = editTextAccountType.getText().toString();

                    // Créer un objet Compte avec les nouvelles données
                    Compte newCompte = new Compte(null, newSolde, newDateCreation, newType);

                    // Appeler la méthode pour ajouter le compte via l'API
                    addCompteToDatabase(newCompte);
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void addCompteToDatabase(Compte newCompte) {
        CompteApi api = CompteService.getInstance(isXmlFormat).create(CompteApi.class);

        api.addCompte(newCompte).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Compte ajouté avec succès", Toast.LENGTH_SHORT).show();
                    fetchComptes(); // Recharger les comptes après l'ajout
                } else {
                    Log.e(TAG, "Erreur lors de l'ajout du compte: " + response.code() + " - " + response.message());
                    Toast.makeText(MainActivity.this, "Erreur lors de l'ajout du compte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Log.e(TAG, "Erreur lors de l'ajout du compte : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
