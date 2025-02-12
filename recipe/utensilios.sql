CREATE TABLE [dbo].[Utensilios]
(
    [UtensilioID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [RecetaID] INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Recetas](RecetaID),
    [PasoID] INT NULL FOREIGN KEY REFERENCES [dbo].[Pasos](PasoID),
    [Nombre] NVARCHAR(255)
);