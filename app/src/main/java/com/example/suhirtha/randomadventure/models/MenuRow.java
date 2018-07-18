package com.example.suhirtha.randomadventure.models;

import com.sysdata.widget.accordion.Item;

public class MenuRow extends Item{

    private String mTitle;
    private String mDescription;

    public static MenuRow create(String title, String description) {
        return new MenuRow(title, description);
    }

    MenuRow(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public int getUniqueId() {
        return hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MenuRow that = (MenuRow) o;

        if (!mTitle.equals(that.mTitle))
            return false;
        return mDescription != null ? mDescription.equals(that.mDescription) : that.mDescription == null;
    }

    @Override
    public int hashCode() {
        int result = mTitle.hashCode();
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        return result;
    }
}
