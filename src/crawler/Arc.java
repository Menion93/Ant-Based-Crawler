package crawler;

/**
 * Created by Andrea on 06/03/2017.
 */
public class Arc
{
    private double from;
    private double to;

    public Arc(double from, double to)
    {
        this.from = from;
        this.to = to;
    }

    public double getFrom()
    {
        return from;
    }

    public double getTo()
    {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arc)) return false;

        Arc arc = (Arc) o;

        if (Double.compare(arc.getFrom(), getFrom()) != 0) return false;
        return Double.compare(arc.getTo(), getTo()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getFrom());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTo());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
