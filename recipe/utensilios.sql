CREATE TABLE [dbo].[Utensilios]
(
    [UtensilioID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [RecetaID] INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Recetas](RecetaID),
    [Nombre] NVARCHAR(255)
);