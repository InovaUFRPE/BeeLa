package com.beela.beela.Helper;

import android.content.Context;

import com.beela.beela.Entidades.Lugar;
import com.beela.beela.R;
import com.beela.beela.DAO.LugarDao;

public class AlimentandoLugares {

    public void gerandoLugares(Context context){
        criarIgrejaDoCarmmo(context);
        criarShoping(context);
        criarClube(context);
        criarBalada(context);
        criarParque(context);
        criarCinema(context);
        criarPraia(context);
        criarFutebol(context);
        criarAcademia(context);
        criarSurf(context);
        criarSkate(context);
        criarCaminhada(context);
        criarArtesMarciais(context);
        criarNatacao(context);
        criarMexicana(context);
        criarDoce(context);
        criarVegetariana(context);
        criarJaponesa(context);
        criarMassa(context);
        criarItaliana(context);
        criarNordestina(context);
        criarSertanejo(context);
        criarRock(context);
        criarAxe(context);
        criarFunk(context);
        criarEletronica(context);
        criarForro(context);
        criarSamba(context);


    }


    public void criarIgrejaDoCarmmo(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.IgrejaCarmo));
        lugar.setDescricao(context.getString(R.string.IgrejaDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarShoping(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Shoping));
        lugar.setDescricao(context.getString(R.string.ShopingDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarClube(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Clube));
        lugar.setDescricao(context.getString(R.string.ClubeDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarCinema(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Cinema));
        lugar.setDescricao(context.getString(R.string.CinemaDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarParque(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Parque));
        lugar.setDescricao(context.getString(R.string.ParqueDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarBalada(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Balada));
        lugar.setDescricao(context.getString(R.string.BaladaDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarPraia(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Praia));
        lugar.setDescricao(context.getString(R.string.PraiaDescricao));
        lugar.setCategoria("Lugar");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarNatacao(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Natação));
        lugar.setDescricao(context.getString(R.string.NataçãoDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarAcademia(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Academia));
        lugar.setDescricao(context.getString(R.string.AcademiaDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarCaminhada(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Caminhada));
        lugar.setDescricao(context.getString(R.string.CaminhadaDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarSurf(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Surf));
        lugar.setDescricao(context.getString(R.string.SurfDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarSkate(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Skate));
        lugar.setDescricao(context.getString(R.string.SkateDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarArtesMarciais(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.ArtesMarciais));
        lugar.setDescricao(context.getString(R.string.ArtesMarciaisDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarFutebol(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Futebol));
        lugar.setDescricao(context.getString(R.string.FutebolDescrição));
        lugar.setCategoria("Esporte");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarVegetariana(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Vegetariana));
        lugar.setDescricao(context.getString(R.string.VegetarianaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarDoce(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Doce));
        lugar.setDescricao(context.getString(R.string.DoceDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }
    public void criarJaponesa(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Japonesa));
        lugar.setDescricao(context.getString(R.string.JaponesaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

    public void criarItaliana(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Italiana));
        lugar.setDescricao(context.getString(R.string.ItalianaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarMexicana(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Mexicana));
        lugar.setDescricao(context.getString(R.string.MexicanaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarMassa(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Massa));
        lugar.setDescricao(context.getString(R.string.MassaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarNordestina(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Nordestina));
        lugar.setDescricao(context.getString(R.string.NordestinaDescrição));
        lugar.setCategoria("Comida");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarSamba(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Samba));
        lugar.setDescricao(context.getString(R.string.SambaDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarAxe(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Axé));
        lugar.setDescricao(context.getString(R.string.AxeDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarEletronica(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Eletrônica));
        lugar.setDescricao(context.getString(R.string.EletronicaDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarFunk(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Funk));
        lugar.setDescricao(context.getString(R.string.FunkDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarForro(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Forró));
        lugar.setDescricao(context.getString(R.string.ForróDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarRock(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Rock));
        lugar.setDescricao(context.getString(R.string.RockDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

     public void criarSertanejo(Context context) {
        Lugar lugar = new Lugar();
        lugar.setNome(context.getString(R.string.Sertanejo));
        lugar.setDescricao(context.getString(R.string.SertanejoDescrição));
        lugar.setCategoria("Música");


        LugarDao bd = new LugarDao();
        bd.getEscrever(context);
        bd.inserir(lugar);
        }

}
