package com.galionet.mapql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;



public class DataTypeHelper {

	public static Object convertToStandardType(Object object){
		Object convertedObject=null;
		if(object instanceof String){
			convertedObject=object;
		}else if(object instanceof Float){
			convertedObject=new BigDecimal((Float)object);
		}else if(object instanceof Double){
			convertedObject=new BigDecimal((Double)object);
		}else if(object instanceof Integer){
			convertedObject=new BigDecimal((Integer)object);
		}else if(object instanceof Long){
			convertedObject=new BigDecimal((Long)object);
		}else if(object instanceof Byte){
			convertedObject=new BigDecimal((Byte)object);
		}else if(object instanceof BigDecimal){
			convertedObject=object;
		}else if(object instanceof Boolean){
			convertedObject=object;
		}else if(object instanceof Character){
			convertedObject=object.toString();
		}else if(object instanceof Timestamp){
			convertedObject=object;
		}
		return convertedObject;
	}
	
	public static DataType getDataType(Object object){
		DataType convertedObject=null;
		if(object instanceof String){
			convertedObject=DataType.STRING;
		}else if(object instanceof Float){
			convertedObject=DataType.FLOAT;
		}else if(object instanceof Double){
			convertedObject=DataType.DOUBLE;
		}else if(object instanceof Integer){
			convertedObject=DataType.INT;
		}else if(object instanceof Long){
			convertedObject=DataType.LONG;
		}else if(object instanceof Byte){
			convertedObject=DataType.BYTE;
		}else if(object instanceof BigDecimal){
			convertedObject=DataType.BIG_DECIMAL;
		}else if(object instanceof Boolean){
			convertedObject=DataType.BOOLEAN;
		}else if(object instanceof Character){
			convertedObject=DataType.STRING;
		}else if(object instanceof Timestamp){
			convertedObject=DataType.TIME_STAMP;
		}else if(object instanceof Date){
			convertedObject=DataType.DATE;
		}
		return convertedObject;
	}
	
	public static Object convertToStandardType(Object object, DataType type){
		Object convertedObject=null;
		if(type == DataType.STRING){
			convertedObject=object.toString();
		}else if(type == DataType.FLOAT){
			convertedObject=(Float)object;
		}else if(type == DataType.DOUBLE){
			convertedObject=(Double)object;
		}else if(type == DataType.INT){
			convertedObject=(Integer)object;
		}else if(type == DataType.LONG){
			convertedObject=(Long)object;
		}else if(type == DataType.BYTE){
			convertedObject=(Byte)object;
		}else if(type == DataType.BIG_DECIMAL){
			convertedObject=new BigDecimal(object.toString());
		}else if(type == DataType.BOOLEAN){
			convertedObject=(Boolean)object;
		}else if(type == DataType.TIME_STAMP){
			convertedObject=(Timestamp)object;
		}else if(type == DataType.DATE){
			convertedObject=new Date((Long) object);
		}
		return convertedObject;
	}

	@SuppressWarnings("deprecation")
	public static Object stringToPrimitiveType(String object, DataType type){
		Object convertedObject=null;
		if(type == DataType.STRING){
			convertedObject=object.toString();
		}else if(type == DataType.FLOAT){
			convertedObject=Float.valueOf(object);
		}else if(type == DataType.DOUBLE){
			convertedObject=Double.valueOf(object);
		}else if(type == DataType.INT){
			convertedObject=Integer.valueOf(object);
		}else if(type == DataType.LONG){
			convertedObject=Long.valueOf(object);
		}else if(type == DataType.BYTE){
			convertedObject=Byte.valueOf(object);
		}else if(type == DataType.BIG_DECIMAL){
			convertedObject=new BigDecimal(object);
		}else if(type == DataType.BOOLEAN){
			convertedObject=Boolean.valueOf(object);
		}else if(type == DataType.TIME_STAMP){
			convertedObject=Timestamp.valueOf(object);
		}else if(type == DataType.DATE){
			convertedObject=Date.parse(object);
		}
		return convertedObject;
	}
	
	public static boolean isStandardType(Object object){
		if(object instanceof String){
			return true;
		}else if(object instanceof Float){
			return true;
		}else if(object instanceof Double){
			return true;
		}else if(object instanceof Integer){
			return true;
		}else if(object instanceof Long){
			return true;
		}else if(object instanceof Byte){
			return true;
		}else if(object instanceof BigDecimal){
			return true;
		}else if(object instanceof Boolean){
			return true;
		}else if(object instanceof Character){
			return true;
		}else if(object instanceof Timestamp){
			return true;
		}else if(object instanceof Date){
			return true;
		}
		return false;
	}
}
