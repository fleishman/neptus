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
 * Version 1.1 only (the "Licence"), appearing in the file LICENCE.md
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
 * Author: José Correia
 * Mar 20, 2012
 */
package pt.lsts.neptus.alarms;

import pt.lsts.neptus.alarms.AlarmManager.AlarmLevel;
import pt.lsts.neptus.console.plugins.EntityStatePanel;

/**
 * A simple implementation of a AlarmProvider meant to be used by components who are able to issue various alarms (see {@link EntityStatePanel} for an example).
 * It is a basic implementation with a full constructor and a getter/setter for every field.
 * @author jqcorreia
 */
public class AlarmProviderImp  implements AlarmProvider {

    AlarmLevel state;
    String name;
    String message;
    
    public AlarmProviderImp(AlarmLevel state, String name, String message) {
        this.state = state;
        this.name = name;
        this.message = message;
    }
    
    @Override
    public AlarmLevel getAlarmState() {
        return state;
    }

    @Override
    public String getAlarmName() {
        return name;
    }

    @Override
    public String getAlarmMessage() {
        return message;
    }

    public void setState(AlarmLevel state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}