package domainapp.dom.carrental;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QRent extends PersistableExpressionImpl<Rent> implements PersistableExpression<Rent>
{
    public static final QRent jdoCandidate = candidate("this");

    public static QRent candidate(String name)
    {
        return new QRent(null, name, 5);
    }

    public static QRent candidate()
    {
        return jdoCandidate;
    }

    public static QRent parameter(String name)
    {
        return new QRent(Rent.class, name, ExpressionType.PARAMETER);
    }

    public static QRent variable(String name)
    {
        return new QRent(Rent.class, name, ExpressionType.VARIABLE);
    }

    public final domainapp.dom.vehicle.QVehicle vehicle;
    public final domainapp.dom.customer.QCustomer customer;
    public final ObjectExpression<org.joda.time.LocalDate> from;
    public final ObjectExpression<org.joda.time.LocalDate> to;
    public final StringExpression note;

    public QRent(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.vehicle = new domainapp.dom.vehicle.QVehicle(this, "vehicle", depth-1);
        }
        else
        {
            this.vehicle = null;
        }
        if (depth > 0)
        {
            this.customer = new domainapp.dom.customer.QCustomer(this, "customer", depth-1);
        }
        else
        {
            this.customer = null;
        }
        this.from = new ObjectExpressionImpl<org.joda.time.LocalDate>(this, "from");
        this.to = new ObjectExpressionImpl<org.joda.time.LocalDate>(this, "to");
        this.note = new StringExpressionImpl(this, "note");
    }

    public QRent(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.vehicle = new domainapp.dom.vehicle.QVehicle(this, "vehicle", 5);
        this.customer = new domainapp.dom.customer.QCustomer(this, "customer", 5);
        this.from = new ObjectExpressionImpl<org.joda.time.LocalDate>(this, "from");
        this.to = new ObjectExpressionImpl<org.joda.time.LocalDate>(this, "to");
        this.note = new StringExpressionImpl(this, "note");
    }
}
