/*
 * Copyright (c) 2004-2013 Universidade do Porto - Faculdade de Engenharia
 * Laboratório de Sistemas e Tecnologia Subaquática (LSTS)
 * All rights reserved.
 * Rua Dr. Roberto Frias s/n, sala I203, 4200-465 Porto, Portugal
 *
 * This file is part of Neptus, Command and Control Framework.
 *
 * Commercial Licence Usage
 * Licencees holding valid commercial Neptus licences may use this file
 * in accordance with the commercial licence agreement provided with the
 * Software or, alternatively, in accordance with the terms contained in a
 * written agreement between you and Universidade do Porto. For licensing
 * terms, conditions, and further information contact lsts@fe.up.pt.
 *
 * European Union Public Licence - EUPL v.1.1 Usage
 * Alternatively, this file may be used under the terms of the EUPL,
 * Version 1.1 only (the "Licence"), appearing in the file LICENSE.md
 * included in the packaging of this file. You may not use this work
 * except in compliance with the Licence. Unless required by applicable
 * law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations at
 * https://www.lsts.pt/neptus/licence.
 *
 * For more information please see <http://lsts.fe.up.pt/neptus>.
 *
 * Author: pdias
 * Jun 23, 2013
 */
package pt.up.fe.dceg.neptus.plugins.envdisp;

import java.util.ArrayList;
import java.util.Date;

public class HFRadarDataPoint implements Comparable<HFRadarDataPoint> {
    //lat lon speed (cm/s)    degree  acquired (Date+Time)    resolution (km) origin
    private double lat;
    private double lon;
    private double speedCmS;
    private double headingDegrees;
    private Date dateUTC;
    private double resolutionKm = -1;
    private String info = "";
    
    private ArrayList<HFRadarDataPoint> historicalData = new ArrayList<>();
    
    public HFRadarDataPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public HFRadarDataPoint getACopyWithoutHistory() {
        HFRadarDataPoint copy = new HFRadarDataPoint(getLat(), getLon());
        copy.setSpeedCmS(getSpeedCmS());
        copy.setHeadingDegrees(getHeadingDegrees());
        copy.setDateUTC(getDateUTC());
        copy.setResolutionKm(getResolutionKm());
        copy.setInfo(getInfo());
        return copy;
    }
    
    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @return the speedCmS
     */
    public double getSpeedCmS() {
        return speedCmS;
    }

    /**
     * @param speedCmS the speedCmS to set
     */
    public void setSpeedCmS(double speedCmS) {
        this.speedCmS = speedCmS;
    }

    /**
     * @return the headingDegrees
     */
    public double getHeadingDegrees() {
        return headingDegrees;
    }

    /**
     * @param headingDegrees the headingDegrees to set
     */
    public void setHeadingDegrees(double headingDegrees) {
        this.headingDegrees = headingDegrees;
    }

    /**
     * @return the dateUTC
     */
    public Date getDateUTC() {
        return dateUTC;
    }

    /**
     * @param dateUTC the dateUTC to set
     */
    public void setDateUTC(Date dateUTC) {
        this.dateUTC = dateUTC;
    }

    /**
     * @return the resolutionKm
     */
    public double getResolutionKm() {
        return resolutionKm;
    }

    /**
     * @param resolutionKm the resolutionKm to set
     */
    public void setResolutionKm(double resolutionKm) {
        this.resolutionKm = resolutionKm;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "Lat:\t" + lat +
        		"\tLon:\t" + lon +
        		"\tSpeed:\t" + speedCmS + 
        		"\tHeading:\t" + headingDegrees + 
        		"\tDate:\t" + dateUTC +
        		"\tResolution:\t" + resolutionKm +
        		"\tInfo:\t" + info;
    }

    /**
     * @return the historicalData
     */
    public ArrayList<HFRadarDataPoint> getHistoricalData() {
        return historicalData;
    }
    
    public boolean calculateMean() {
        if (historicalData.size() == 0)
            return false;
        Date mostRecentDate = null;
        double meanSpeed = 0;
        double meanHeading = 0;
        double size = historicalData.size();
        for (HFRadarDataPoint dp : historicalData) {
            if (mostRecentDate == null || !mostRecentDate.after(dp.dateUTC))
                mostRecentDate = dp.dateUTC;
            meanSpeed += dp.speedCmS;
            meanHeading += dp.headingDegrees;
        }
        meanSpeed = meanSpeed / size;
        meanHeading = meanHeading / size;
        
        setSpeedCmS(meanSpeed);
        setHeadingDegrees(meanHeading);
        setDateUTC(mostRecentDate);
        
        return true;
    }
    
    public void purgeAllBefore(Date date) {
        if (date == null || historicalData.size() == 0)
            return;
        for (HFRadarDataPoint dp : historicalData.toArray(new HFRadarDataPoint[0])) {
            if (dp.getDateUTC().before(date))
                historicalData.remove(dp);
        }
    }

    @Override
    public int compareTo(HFRadarDataPoint o) {
        if (Double.compare(lat, o.lat) == 0 && Double.compare(lon, o.lon) == 0)
            return 0;
        else
            return 1; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof HFRadarDataPoint))
            return false;
        return compareTo((HFRadarDataPoint) obj) == 0 ? true : false;
    }
    
    public static String getId(HFRadarDataPoint hfrdp) {
        return hfrdp.lat + ":" + hfrdp.lon;
    }
}