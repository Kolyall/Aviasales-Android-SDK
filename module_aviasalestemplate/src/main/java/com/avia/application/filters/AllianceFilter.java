package com.avia.application.filters;

import android.content.Context;

import com.avia.application.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import ru.aviasales.core.search.object.AirlineData;
import ru.aviasales.core.search.object.Flight;
import ru.aviasales.expandedlistview.view.BaseCheckedText;

public class AllianceFilter extends BaseListFilter implements Serializable {

    @Setter private transient Context context;
    @Getter @Setter private List<BaseCheckedText> allianceList;

    public AllianceFilter(Context context) {
        this.context = context;
        allianceList = new ArrayList<>();
    }

    public AllianceFilter(Context context, AllianceFilter allianceFilter) {
        if (allianceFilter.getAllianceList() == null) return;

        allianceList = new ArrayList<>();
        for (int i = 0; i < allianceFilter.getAllianceList().size(); i++) {
            allianceList.add(new BaseCheckedText(allianceFilter.getAllianceList().get(i)));
        }
        this.context = context;
    }

    public void addAlliance(String alliance) {
        allianceList.add(new BaseCheckedText(alliance));
    }

    public boolean isActual(String alliance) {
        for (BaseCheckedText checkedAlliance : allianceList) {
            if (checkedAlliance.getName().equals(alliance) && !checkedAlliance.isChecked()) {
                return false;
            }
            if (context.getString(R.string.filters_another_alliances).equals(checkedAlliance.getName()) &&
                    alliance == null &&
                    !checkedAlliance.isChecked()) {
                return false;
            }
        }
        return true;
    }

    public void addAlliancesData(Map<String, AirlineData> airlineMap) {
        for (String airline : airlineMap.keySet()) {
            if (airlineMap.get(airline) != null && airlineMap.get(airline).getAllianceName() != null) {
                BaseCheckedText cAlliance = new BaseCheckedText(airlineMap.get(airline).getAllianceName());

                if (!allianceList.contains(cAlliance) && cAlliance.getName() != null) {
                    allianceList.add(cAlliance);
                }
            }
        }
        BaseCheckedText anotherAlliances = new BaseCheckedText();
        anotherAlliances.setChecked(true);
        anotherAlliances.setName(context.getString(R.string.filters_another_alliances));
        allianceList.add(anotherAlliances);
    }

    public void addAlliancesData(Map<String, AirlineData> airlineMap, List<Flight> flights) {
        for (String airline : airlineMap.keySet()) {
            if (airlineMap.get(airline) != null && airlineMap.get(airline).getAllianceName() != null) {
                BaseCheckedText checkedAlliance = new BaseCheckedText(airlineMap.get(airline).getAllianceName());

                for (Flight flight : flights) {
                    if (!allianceList.contains(checkedAlliance) && checkedAlliance.getName() != null &&
                            flight.getOperatingCarrier().equalsIgnoreCase(airline)) {
                        allianceList.add(checkedAlliance);
                    }
                }
            }
        }
        BaseCheckedText anotherAlliances = new BaseCheckedText();
        anotherAlliances.setChecked(true);
        anotherAlliances.setName(context.getString(R.string.filters_another_alliances));
        if (!allianceList.contains(anotherAlliances)) {
            allianceList.add(anotherAlliances);
        }
    }

    public void sortByName() {
        Collections.sort(allianceList, BaseCheckedText.nameComparator);
    }


    @Override
    public List<BaseCheckedText> getCheckedTextList() {
        return allianceList;
    }
}