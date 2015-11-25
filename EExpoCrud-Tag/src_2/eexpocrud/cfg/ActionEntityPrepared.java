package eexpocrud.cfg;



@SuppressWarnings("serial")
public abstract class ActionEntityPrepared<E extends Object> extends ActionPrepared<E>{
 
	
	public ActionEntityPrepared(String jspToDispatch) {
		super(jspToDispatch);
	}

	public E prepare(){
		E result = null ;
		if(entityId == null){
			 try {
				result = (E) this.bo.entityClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}else{
			result = this.bo.read(entityId);
		}

		 return result;
	 }
 }