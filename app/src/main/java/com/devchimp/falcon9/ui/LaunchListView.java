package com.devchimp.falcon9.ui;

import java.util.List;

public interface LaunchListView {
    void showList(boolean show);
    void showLoading(boolean show);
    void updateLaunches(List<LaunchViewModel> launches);
    void showError(boolean show);
}
