package nescaupower.br.com.keepsoft.Utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import nescaupower.br.com.keepsoft.Enum.Perfil;

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
        } else if (perfil.equals(Perfil.PRODUCTOWNER.toString())) {
            return Perfil.PRODUCTOWNER;
        } else if (perfil.equals(Perfil.SCRUM_MASTER.toString())) {
            return Perfil.SCRUM_MASTER;
        } else {
            throw new IllegalArgumentException("Tipo de Perfil não reconhecido");
        }
    }

    @TypeConverter
    public static String getPerfilString(Perfil perfil) {
        return perfil.toString();
    }

}
