USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Shipment]    Script Date: 10/18/2015 6:02:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Shipment](
	[ID] [int] NOT NULL,
	[Name] [nchar](50) NOT NULL,
	[NameDirty] [bit] NOT NULL,
	[Status] [nchar](20) NOT NULL,
	[StatusDirty] [bit] NOT NULL,
	[ToLoc] [int] NOT NULL,
	[ToLocDirty] [bit] NOT NULL,
	[LastLoc] [int] NOT NULL,
	[LastLocDirty] [bit] NOT NULL,
	[FromLoc] [int] NOT NULL,
	[FromLocDirty] [bit] NOT NULL,
	[Description] [nchar](140) NOT NULL,
	[DescriptionDirty] [bit] NOT NULL,
 CONSTRAINT [PK_Shipment] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

