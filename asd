if (slug == null) {
            try {
                UUID uuid = UUID.fromString(req.getParameter("uuid"));
                Order order = api.getOrder(uuid);
                SalesRepresentative salesRepresentative = (SalesRepresentative) req.getSession().getAttribute("user");
                int ret = api.updateSalesRep(order, salesRepresentative);
                resp.sendRedirect("orders");
            } catch (OrderNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        else {

            UUID uuid = UUID.fromString(slug);
            req.setAttribute("slug", slug);

            if (req.getParameter("order-offer") != null) {
                int offer = Integer.parseInt(req.getParameter("offer"));
                try {
                    api.updateOffer(uuid, offer);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                int carportWidth;
                int carportLength;
                Carport.roofTypes roofType;
                String activeRoofAngle;
                int roofAngle;
                int roof_material;

                int shedWidth;
                int shedLength;

                int id = -1;

                carportLength = Integer.parseInt(req.getParameter("carport-length"));
                carportWidth = Integer.parseInt(req.getParameter("carport-width"));
                activeRoofAngle = req.getParameter("roof-angle");
                if (activeRoofAngle != null) {
                    roofType = Carport.roofTypes.ANGLED;
                    roofAngle = Integer.parseInt(req.getParameter("roof-angle"));
                    roof_material = Integer.parseInt(req.getParameter("roof_angled_material"));
                }
                else {
                    roofType = Carport.roofTypes.FLAT;
                    roof_material = Integer.parseInt(req.getParameter("roof_flat_material"));
                    roofAngle = -1;
                }

                Carport carport = new Carport(id, carportWidth, carportLength, roofType, roofAngle, roof_material);

                //create shed
                Shed shed = null;
                if (req.getParameter("shed-length") != null) {
                    shedWidth = Integer.parseInt(req.getParameter("shed-width"));
                    shedLength = Integer.parseInt(req.getParameter("shed-length"));
                    shed = new Shed(id, shedWidth, shedLength);
                }

                //create order
                int carportId = api.getCarportIdFromUuid(uuid);
                api.updateOrder(carportId, carport, shed);
            }
            resp.sendRedirect(slug);