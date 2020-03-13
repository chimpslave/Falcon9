package com.devchimp.falcon9.ui;

import com.devchimp.falcon9.objects.Launch;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LaunchViewModel {
    public abstract String missionName();

    public abstract String launchDate();

    public abstract boolean missionSuccess();

    public abstract String missionPatch();

    Builder toBuilder() {
        return builder()
                .missionName(missionName())
                .launchDate(launchDate())
                .missionSuccess(missionSuccess())
                .missionPatch(missionPatch());
    }

    private static LaunchViewModel create(String missionName, String launchDate, boolean missionSuccess, String missionPatch) {
        return builder()
                .missionName(missionName)
                .launchDate(launchDate)
                .missionSuccess(missionSuccess)
                .missionPatch(missionPatch)
                .build();
    }

    private static Builder builder() {
        return new AutoValue_LaunchViewModel.Builder()
                .missionName("")
                .launchDate("")
                .missionSuccess(false)
                .missionPatch("");
    }

    @AutoValue.Builder
    static abstract class Builder {
        abstract Builder missionName(String missionName);

        abstract Builder launchDate(String launchDate);

        abstract Builder missionSuccess(boolean missionSuccess);

        abstract Builder missionPatch(String missionPatch);

        abstract LaunchViewModel build();
    }

    static class Factory {
        Factory() {
        }

        LaunchViewModel from(Launch launch) {
            final String imageUri = launch.getLinks().getMission_patch() != null ? launch.getLinks().getMission_patch() : "";
            return create(launch.getMission_name(), launch.getLaunch_date_utc(), launch.isLaunch_success(), imageUri);
        }
    }
}
