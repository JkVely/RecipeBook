protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String recetaId = request.getParameter("id");

    if (recetaId != null) {
        try {
            int id = Integer.parseInt(recetaId);
            RecetasContainer container = (RecetasContainer) request.getSession().getAttribute("recetasContainer");

            if (container == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            Receta receta = container.selectReceta(id);

            if (receta == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            request.getSession().setAttribute("receta", receta);
            request.getSession().setAttribute("pasos", receta.getPasos());
            request.getRequestDispatcher("receta.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
        }
    } else {
        response.sendRedirect("error.jsp");
    }
}
