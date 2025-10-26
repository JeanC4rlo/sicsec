/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util;

import br.cefetmg.sicsec.Model.Util.Enum.Turno;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author davig
 */
public class GradeHorarios {
    
    private static final Map<Integer, Horario> HORARIOS = new LinkedHashMap<>();
    
    static {
        
        // PRIMEIRO E SEGUNDO HORÁRIOS DA MANHÃ 7:00 - 8:40
        HORARIOS.put(0, new Horario(LocalTime.of(7, 0), LocalTime.of(7, 50), Turno.MANHA, 1));
        HORARIOS.put(1, new Horario(LocalTime.of(7, 50), LocalTime.of(8, 40), Turno.MANHA, 2));
        
        // TERCEIRO E QUARTO HORÁRIOS DA MANHÃ 8:50 - 10:30
        HORARIOS.put(2, new Horario(LocalTime.of(8, 50), LocalTime.of(9, 20), Turno.MANHA, 3));
        HORARIOS.put(3, new Horario(LocalTime.of(9, 20), LocalTime.of(10, 30), Turno.MANHA, 4));
        
        // QUINTO E SEXTO HORÁRIOS DA MANHÃ 10:40 - 12:20
        HORARIOS.put(4, new Horario(LocalTime.of(10, 40), LocalTime.of(11, 30), Turno.MANHA, 5));
        HORARIOS.put(5, new Horario(LocalTime.of(11, 30), LocalTime.of(12, 20), Turno.MANHA, 6));
        
        // HORÁRIO DE ALMOÇO 12:20 - 13:00
        HORARIOS.put(-1, new Horario(LocalTime.of(12, 20), LocalTime.of(13, 00), Turno.INTERVALO_ALMOCO, 0));
        
        // PRIMEIRO E SEGUNDO HORÁRIOS DA TARDE 13:00 - 14:40
        HORARIOS.put(6, new Horario(LocalTime.of(13, 0), LocalTime.of(13, 50), Turno.TARDE, 1));
        HORARIOS.put(7, new Horario(LocalTime.of(13, 50), LocalTime.of(14, 40), Turno.TARDE, 2));
        
        // TERCEIRO E QUARTO HORÁRIOS DA TARDE 14:50 - 16:30
        HORARIOS.put(8, new Horario(LocalTime.of(14, 50), LocalTime.of(15, 40), Turno.TARDE, 3));
        HORARIOS.put(9, new Horario(LocalTime.of(15, 40), LocalTime.of(16, 30), Turno.TARDE, 4));
        
        // QUINTO E SEXTO HORÁRIOS DA TARDE 16:40 - 18:20
        HORARIOS.put(10, new Horario(LocalTime.of(16, 40), LocalTime.of(17, 30), Turno.TARDE, 5));
        HORARIOS.put(11, new Horario(LocalTime.of(17, 30), LocalTime.of(18, 20), Turno.TARDE, 6));
        
        // HORÁRIO DO JANTAR 18:20 - 19:00
        HORARIOS.put(-2, new Horario(LocalTime.of(18, 20), LocalTime.of(19, 00), Turno.INTERVALO_JANTAR, 0));
        
        // HORÁRIO APÓS O FIM DAS AULAS 18:20, ATÉ O FECHAMENTO DO CEFET 23:00
        HORARIOS.put(12, new Horario(LocalTime.of(19,00), LocalTime.of(23, 00), Turno.NOITE, 0));
        
        // HORÁRIO APÓS O FECHAMENTO DO CEFET, ATÉ A ABERTURA NO DIA SEGUINTE, IGNORANDO FINS DE SEMANA
        HORARIOS.put(13, new Horario(LocalTime.of(23,00), LocalTime.of(7, 00), Turno.FORA, 0));
        
    }
    
    public static Horario IntToHorario(int i) {
        return HORARIOS.get(i);
    }
    
    public static int HorarioToInt(Horario intervaloHorario) {
        
        for (int _i = -2; _i <= 12; _i++) {
            
            Horario _horario = HORARIOS.get(_i);
            
            if (intervaloHorario.equals(_horario)) 
                return _i;
            
        }
        
        return 13;
        
    }
    
    public static Horario LocalizarHorario(LocalTime hora) {
        
        for (int _i = -2; _i <= 12; _i++) {
            
            Horario _horario = HORARIOS.get(_i);
            
            if (hora.isBefore(_horario.getFim()))
                return _horario;
            
        }
        
        return null;
        
    }
    
}