CREATE TABLE [dbo].[Pasos]
(
    [PasoID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [RecetaID] INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Recetas](RecetaID),
    [Descripcion] NVARCHAR(MAX),
    [Tiempo] INT,
    [Imagen] NVARCHAR(255)
);