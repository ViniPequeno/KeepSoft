package nescaupower.br.com.keepsoft.Utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Dificuldade;
import nescaupower.br.com.keepsoft.Enum.Perfil;
import nescaupower.br.com.keepsoft.Enum.Prioridade;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Perfil stringToPerfil(String perfil) {
        if (perfil.equals(Perfil.TEAM.toString())) {
            return Perfil.TEAM;
        } else if (perfil.equals(Perfil.PRODUCT_OWNER.toString())) {
            return Perfil.PRODUCT_OWNER;
        } else if (perfil.equals(Perfil.SCRUM_MASTER.toString())) {
            return Perfil.SCRUM_MASTER;
        } else {
            throw new IllegalArgumentException("Tipo de Perfil não reconhecido");
        }
    }

    @TypeConverter
    public static String getDificuldadeString(Dificuldade dificuldade) {
        return dificuldade.toString();
    }

    @TypeConverter
    public static Dificuldade stringToDificuldade(String dificuldade) {
        if (dificuldade.equals(Dificuldade.FACIL.toString())) {
            return Dificuldade.FACIL;
        } else if (dificuldade.equals(Dificuldade.MEDIO.toString())) {
            return Dificuldade.MEDIO;
        } else if (dificuldade.equals(Dificuldade.DIFICL.toString())) {
            return Dificuldade.DIFICL;
        } else {
            throw new IllegalArgumentException("Tipo de Dificuldade não reconhecido");
        }
    }

    @TypeConverter
    public static String getPrioridadeString(Prioridade perfil) {
        return perfil.toString();
    }

    @TypeConverter
    public static Prioridade stringToPrioridade(String perfil) {
        if (perfil.equals(Prioridade.BAIXA.toString())) {
            return Prioridade.BAIXA;
        } else if (perfil.equals(Prioridade.MEDIA.toString())) {
            return Prioridade.MEDIA;
        } else if (perfil.equals(Prioridade.ALTA.toString())) {
            return Prioridade.ALTA;
        } else if (perfil.equals(Prioridade.MUITO_ALTA.toString())) {
            return Prioridade.MUITO_ALTA;
        } else {
            throw new IllegalArgumentException("Tipo de Perfil não reconhecido");
        }
    }

    @TypeConverter
    public static String getPerfilString(Perfil perfil) {
        return perfil.toString();
    }
}
