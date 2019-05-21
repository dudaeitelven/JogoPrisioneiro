package com.example.jogo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;

import com.example.jogo.grafo.CaminhoHeuristico;
import com.example.jogo.grafo.Grafo;
import com.example.jogo.grafo.GrafoListaAdjasencia;

import java.util.LinkedList;
import java.util.List;

public class Jogo extends View  {

    private int linhas = 11;
    private int colunas = 11; //tamanho do mapa

    private int size = 95; //tamanho de um quadrado (vertice) no mapa - muda-lo influencia no tamanho interface do jogo (para maior ou para menor)

    private int pontos = 426; //indica quantos movimentos ainda pode-se fazer

    private int VERTICES = linhas * colunas; //total de vertices

    private int posicaoPrisioneiro = (linhas / 2) * linhas + (colunas / 2); //posição inicial do prisioneiro

    private boolean venceu = false;
    private boolean perdeu = false;
    private Grafo[] grafo = null;
    private GrafoListaAdjasencia gLA = null;

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
        gLA.inicializaObstaculos();

        for (int y = 20, i = 0; y < linhas * size; y += size) {
            for (int x = 20; x < colunas * size; x += size, i++) {
                grafo[i].setPosX(x);
                grafo[i].setPosY(y);
            }
        }

    }

    @Override
    public void onDraw(Canvas canvas){

        for(int i = 0; i < VERTICES; i++){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor((Integer) grafo[i].getNome());
            canvas.drawRect(grafo[i].getPosX(),grafo[i].getPosY(),size+grafo[i].getPosX(),size+grafo[i].getPosY(),paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(grafo[i].getPosX(),grafo[i].getPosY(),size+grafo[i].getPosX(),size+grafo[i].getPosY(),paint);
        }

        if(venceu) {
            mensagem("Atenção", "Vacê venceu!");
        }
        if(perdeu) {
            mensagem("Atenção", "Vacê perdeu!");
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
                    pontos--;
                    updatePrisioneiro(); //atualiza o proximo passo do prisioneiro
                    postInvalidate();
                    return true;
                }
            }
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
                        System.exit(0);
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
                if(grafo[caminhoReal.get(1).getVertice()].getNome().equals(Color.GREEN)) { //1 estado final (alcançou o objetivo)
                    perdeu = true;
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
