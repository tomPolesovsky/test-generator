package domainapp.dom.vehicle;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QVehicle extends PersistableExpressionImpl<Vehicle> implements PersistableExpression<Vehicle>
{
    public static final QVehicle jdoCandidate = candidate("this");

    public static QVehicle candidate(String name)
    {
        return new QVehicle(null, name, 5);
    }

    public static QVehicle candidate()
    {
        return jdoCandidate;
    }

    public static QVehicle parameter(String name)
    {
        return new QVehicle(Vehicle.class, name, ExpressionType.PARAMETER);
    }

    public static QVehicle variable(String name)
    {
        return new QVehicle(Vehicle.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression type;
    public final NumericExpression<Integer> price;

    public QVehicle(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.type = new StringExpressionImpl(this, "type");
        this.price = new NumericExpressionImpl<Integer>(this, "price");
    }

    public QVehicle(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.type = new StringExpressionImpl(this, "type");
        this.price = new NumericExpressionImpl<Integer>(this, "price");
    }
}
