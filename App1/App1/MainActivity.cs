using System;
using System.Collections.Generic;
using System.IO;
using Android.App;
using Android.OS;
using Android.Runtime;
using Android.Support.Design.Widget;
using Android.Support.V7.App;
using Android.Views;
using Android.Widget;

namespace App1
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme.NoActionBar", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        Button btnBuscar, btnNovaCidade, btnNovoCaminho;
        Cidade cidade;
        List<Cidade> listaCidades;
        List<string> nomesCidades;
        Spinner spinnerDe;
        Spinner spinnerPara;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.activity_main);
            btnBuscar = FindViewById<Button>(Resource.Id.btnBuscar);
            btnNovaCidade = FindViewById<Button>(Resource.Id.btnNovaCidade);
            btnNovoCaminho = FindViewById<Button>(Resource.Id.btnNovoCaminho);

            spinnerDe = FindViewById<Spinner>(Resource.Id.spinnerDe);
            spinnerPara = FindViewById<Spinner>(Resource.Id.spinnerPara);


            listaCidades = new List<Cidade>();
            nomesCidades = new List<string>();

            nomesCidades = Cidades();

            ArrayAdapter adapter = new ArrayAdapter(this, Android.Resource.Layout.SimpleListItem1, nomesCidades);
            //vincula o adaptador ao controle spinner
            spinnerDe.Adapter = adapter;


            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cbDe,android.R.layout.simple_spinner_item);
        }

        public List<string> Cidades()
        {
            int id;
            String nome;
            double distanciaX;
            double distanciaY;

            int inicioIndice = 0;
            int tamanhoIndice = 2;
            int inicioNome = tamanhoIndice + inicioIndice;
            int tamanhoNome = 16;
            int inicioX = inicioNome + tamanhoNome;
            int tamanhoX = 6;
            int inicioY = inicioX + tamanhoX;
            int tamanhoY = 5;

            List<string> ret = new List<string>();
            String linha;
            try
            {
                StreamReader br = new StreamReader("Cidades.txt");
                linha = br.ReadLine();

                while (linha != null)
                {
                    String s = linha.Substring(inicioIndice, tamanhoIndice);
                    s = s.Trim();
                    id = Convert.ToInt32(s);

                    nome = linha.Substring(inicioNome, tamanhoNome).Trim();

                    linha = linha.Replace(',', '.');
                    String[] vet = linha.Substring(inicioX).Split(" ");

                    distanciaX = Convert.ToDouble(vet[0]);
                    distanciaY = Convert.ToDouble(vet[1]);

                    cidade = new Cidade(id, nome, distanciaX, distanciaY);
                    listaCidades.Add(cidade);

                    ret.Add(cidade.getNomeCidade());

                    linha = br.ReadLine();
                }
            }
            catch (Exception e)
            {
            }
            return ret;
        }
        public override bool OnCreateOptionsMenu(IMenu menu)
        {
            MenuInflater.Inflate(Resource.Menu.menu_main, menu);
            return true;
        }

        public override bool OnOptionsItemSelected(IMenuItem item)
        {
            int id = item.ItemId;
            if (id == Resource.Id.action_settings)
            {
                return true;
            }

            return base.OnOptionsItemSelected(item);
        }

        private void FabOnClick(object sender, EventArgs eventArgs)
        {
            View view = (View) sender;
            Snackbar.Make(view, "Replace with your own action", Snackbar.LengthLong)
                .SetAction("Action", (Android.Views.View.IOnClickListener)null).Show();
        }
	}
}

