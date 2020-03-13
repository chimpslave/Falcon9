package com.devchimp.falcon9.objects;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Launch {
    @PrimaryKey
    int flight_number;
    int launch_year;
    @Embedded
    RocketInfo rocket;
    private String mission_name;
    private String launch_date_utc;
    private boolean launch_success;
    @Embedded
    private Links links;

    public String getMission_name() {
        return mission_name;
    }

    public String getLaunch_date_utc() {
        return launch_date_utc;
    }

    public boolean isLaunch_success() {
        return launch_success;
    }

    public Links getLinks() {
        return links;
    }
}

class RocketInfo {
    public String rocket_id;
    String rocket_name;
    String rocket_type;
}

