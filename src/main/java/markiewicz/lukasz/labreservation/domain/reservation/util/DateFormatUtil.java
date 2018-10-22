/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    public static String formatted(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }
}
