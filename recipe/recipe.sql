CREATE TABLE [dbo].[Recetas]
(
    [RecetaID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [UserID] INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Users](UserID),
    [Nombre] NVARCHAR(255) NOT NULL,
    [Tipo] NVARCHAR(50),
    [Imagen] NVARCHAR(255),
    [Descripcion] NVARCHAR(MAX),
    [Tiempo] INT,
    [Valor] DECIMAL(3,2)
);