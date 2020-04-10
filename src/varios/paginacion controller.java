	public static ModelAndView paginacion(Request request, Response response) {
    	int page;
 		if (currentSession == null) {
 			response.status(500);
 			return new ModelAndView(null, "/error500-1.hbs");
 		}
 		if (request.queryParams("page") == null) {
 			page = 0;
 		}else {
 			page = Integer.parseInt(request.queryParams("page"));
 		}
 		
     	
		List<RequestPrenda> reqPrendas = RepoPrendas.getPrendasPag(page).stream().map(pr -> new RequestPrenda(pr)).collect(Collectors.toList());
         List<RequestPrenda> ropas = null;
         if (request.queryParams("tipo") == null || request.queryParams("tipo").isEmpty()) {
         	ropas = reqPrendas;
         } 
         else {        
         	ropas = reqPrendas.stream().filter(pr -> 
         	pr.getTipo().equals(request.queryParams("tipo"))).collect(Collectors.toList());
         }
 	    Map<String, Object> map = new HashMap<>();
         map.put("listadoReqPrendas", ropas);
         map.put("sgte", page+1);
         return new ModelAndView(map, "ropas.hbs");
     }