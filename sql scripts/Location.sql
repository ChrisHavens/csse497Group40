USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Location]    Script Date: 10/18/2015 6:01:25 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Location](
	[ID] [int] NOT NULL,
	[Name] [nchar](50) NOT NULL,
	[Lat] [int] NOT NULL,
	[Lon] [int] NOT NULL,
	[NameDirty] [bit] NOT NULL,
	[LatDirty] [bit] NOT NULL,
	[LonDirty] [bit] NOT NULL,
 CONSTRAINT [PK_Location] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

