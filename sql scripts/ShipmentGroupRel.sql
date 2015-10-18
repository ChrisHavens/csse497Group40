USE [Humanitarian]
GO

/****** Object:  Table [dbo].[ShipmentGroupRel]    Script Date: 10/18/2015 6:02:43 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ShipmentGroupRel](
	[ShipmentID] [int] NOT NULL,
	[SuperID] [int] NOT NULL
) ON [PRIMARY]

GO

