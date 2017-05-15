package domainapp.dom.carrental;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QVehicleType extends PersistableExpressionImpl<VehicleType> implements PersistableExpression<VehicleType>
{
    public static final QVehicleType jdoCandidate = candidate("this");

    public static QVehicleType candidate(String name)
    {
        return new QVehicleType(null, name, 5);
    }

    public static QVehicleType candidate()
    {
        return jdoCandidate;
    }

    public static QVehicleType parameter(String name)
    {
        return new QVehicleType(VehicleType.class, name, ExpressionType.PARAMETER);
    }

    public static QVehicleType variable(String name)
    {
        return new QVehicleType(VehicleType.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression brand;
    public final StringExpression type;

    public QVehicleType(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.brand = new StringExpressionImpl(this, "brand");
        this.type = new StringExpressionImpl(this, "type");
    }

    public QVehicleType(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.brand = new StringExpressionImpl(this, "brand");
        this.type = new StringExpressionImpl(this, "type");
    }
}
