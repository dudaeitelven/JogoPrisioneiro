package com.example.jogo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.jogo.grafo.CaminhoHeuristico;
import com.example.jogo.grafo.Grafo;
import com.example.jogo.grafo.GrafoListaAdjasencia;

import java.util.LinkedList;
import java.util.List;

public class Jogo extends View {

    private int width;
    private int height;

    private int xVoltar;
    private int yVoltar;

    private int voltarLargura = 270;
    private int voltarAltura = 38;

    private int linhas = 11; //teste
    private int colunas = 11; //tamanho do mapa

    private int size = 0; //tamanho de um quadrado (vertice) no mapa - muda-lo influencia no tamanho interface do jogo (para maior ou para menor)

    private int pontos = 121; //indica quantos movimentos ainda pode-se fazer

    private int VERTICES = linhas * colunas; //total de vertices

    private int posicaoPrisioneiro = (linhas / 2) * linhas + (colunas / 2); //posição inicial do prisioneiro

    private boolean venceu = false;
    private boolean perdeu = false;
    private Grafo[] grafo = null;
    private GrafoListaAdjasencia gLA = null;
    private Player player;

    private Paint paint;

    private Context context;

    public Jogo(Context context){
        super(context);
        this.context = context;

        paint = new Paint();

        grafo = new Grafo[VERTICES];
        gLA = new GrafoListaAdjasencia(grafo, VERTICES, linhas, colunas);
        gLA.inicializaGrafo();
        gLA.inicializaArestas();
        player = Player.getInstance();

        if (player.getDificuldade() == 2){
            gLA.inicializaObstaculos(15);
            pontos -= 15;
        }
        if (player.getDificuldade() == 1){
            gLA.inicializaObstaculos(20);
            pontos -= 20;
        }
        if (player.getDificuldade() == 0){
            gLA.inicializaObstaculos(25);
            pontos -= 25;
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        width = canvas.getWidth();
        height = canvas.getHeight();

        size = (width - 40) / linhas;

        xVoltar = ((width / 2) - voltarLargura);
        yVoltar = ((size * linhas) + 40 + ( size * 3));

        for (int y = 20, i = 0; y < linhas * size; y += size) {
            for (int x = 20; x < colunas * size; x += size, i++) {
                grafo[i].setPosX(x);
                grafo[i].setPosY(y);
            }
        }

        for(int i = 0; i < VERTICES; i++){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor((Integer) grafo[i].getNome());
            canvas.drawRect(grafo[i].getPosX(),grafo[i].getPosY(),size+grafo[i].getPosX(),size+grafo[i].getPosY(),paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(grafo[i].getPosX(),grafo[i].getPosY(),size+grafo[i].getPosX(),size+grafo[i].getPosY(),paint);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        canvas.drawText("Score: " + Integer.toString(pontos) , 20, (size * linhas) + 40 + size, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);

        if(player.getNome().equals("")){
            canvas.drawText("Player: Anonimo" , 20, (size * linhas) + 40 + size + size, paint);
        } else{
            canvas.drawText("Player: " + player.getNome() , 20, (size * linhas) + 40 + size + size, paint);
        }


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.voltar);
        canvas.drawBitmap(bitmap, xVoltar, yVoltar,null);

        if(venceu) {


            mensagem("Atenção", "Você venceu!");

            //Salva os dados do vencedor
        }
        if(perdeu) {
            perdeu = false;
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.e_um_fracassado);
            mediaPlayer.start();

            mensagem("Atenção", "Você perdeu!");

            //Salva os dados do vencedor
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        for (int i = 0; i < VERTICES; i++) {
            if((touchX >= grafo[i].getPosX()) && (touchX < (grafo[i].getPosX() + size)) && (touchY >= grafo[i].getPosY()) && (touchY < (grafo[i].getPosY() + size))){
                if(!grafo[i].getNome().equals(Color.BLACK) && !grafo[i].getNome().equals(Color.RED)) {
                    gLA.removeVertice(i); //obstaculo
                    if(player.getDificuldade() == 0){
                        pontos -= 3;
                    }else if(player.getDificuldade() == 1){
                        pontos -= 2;
                    } else if(player.getDificuldade() == 2){
                        pontos -= 1;
                    }

                    updatePrisioneiro(); //atualiza o proximo passo do prisioneiro
                    postInvalidate();
                    return true;
                }
            }
        }

        if((touchY >= yVoltar) && (touchY <= (yVoltar + voltarAltura))){
            ((AppCompatActivity)context).onBackPressed();
        }

        postInvalidate();
        return true;
    }

    private void mensagem(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);

        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((AppCompatActivity)context).onBackPressed();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updatePrisioneiro() { //atualiza a proxima posição do prisioneiro ao executar o a*
        List<CaminhoHeuristico> caminhoReal = new LinkedList<>();
        CaminhoHeuristico[] caminho = new CaminhoHeuristico[VERTICES];
        int pai;

        pai = gLA.buscaEstrela(posicaoPrisioneiro, caminho);
        while(pai!=-1) {
            caminhoReal.add(0, caminho[pai]);
            pai = caminho[pai].getPai();
        }

        if(!caminhoReal.isEmpty()) { //verifica os 2 estados finais possiveis
            if(caminhoReal.size() >= 2) {
                if(grafo[caminhoReal.get(1).getVertice()].getNome().equals(Color.GREEN) || pontos <= 0) { //1 estado final (alcançou o objetivo)
                    perdeu = true;
                    if(pontos < 0) pontos = 0;
                }
                grafo[caminhoReal.get(0).getVertice()].setNome(Color.WHITE);
                grafo[caminhoReal.get(1).getVertice()].setNome(Color.RED);
                posicaoPrisioneiro = caminhoReal.get(1).getVertice();
            }else { //2 estado final (impossivel alcançar o objetivo)
                venceu = true;
            }

        }

    }

}
