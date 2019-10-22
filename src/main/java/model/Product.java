package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Product implements Serializable {
    private long id;
    private String name;
    private String description;
    private long create_date;
    private long place_storage;
    private boolean reserved;
//    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public Product() {
    }

    public Product(long id, String name, String description, long create_date, long place_storage, boolean reserved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.create_date = create_date;
        this.place_storage = place_storage;
        this.reserved = reserved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreate_date() {
        return create_date;
    }

    public String getCreate_dateAsString() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(create_date);

        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1)
            day = "0" + day;

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() == 1)
            month = "0" + month;

        return calendar.get(Calendar.YEAR) + "-" + month + "-" + day;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public long getPlace_storage() {
        return place_storage;
    }

    public void setPlace_storage(long place_storage) {
        this.place_storage = place_storage;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + description + "\t" + getCreate_dateAsString() + "\t" + place_storage + "\t" + reserved;
    }

        @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        if (!o.getClass().getName().equals(this.getClass().getName())) return false;

        Product temp = (Product) o;

        if (temp.id != id) return false;
        if (temp.name != null ? !temp.name.equals(name) : name != null) return false;
        if (temp.description != null ? !temp.description.equals(description) : description != null) return false;
        if (temp.create_date != create_date) return false;
        if (temp.place_storage != place_storage) return false;
        if (temp.reserved != reserved) return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = PRIME * result + (int)id;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + ((description == null) ? 0 : description.hashCode());
        result = PRIME * result + (int)create_date;
        result = PRIME * result + (int)place_storage;
        result = PRIME * result + (reserved ? 0 : 1);
        return result;
    }
}
