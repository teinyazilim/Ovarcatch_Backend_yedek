#Import all Necessary Library
import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt

def convertmonzo(pdfPath):

  pdf = tabula.read_pdf(""+pdfPath+"", pages="all")
  pagecount =len(pdf)

  first = []
  schemeFirst = {
    "page": 1,
    "extraction_method": "guess",
    "x1": 62.29285714285714,
    "x2": 534.4071428571428,
    "y1": 428.9999785714285,
    "y2": 763.4142642857142,
    "width": 472.11428571428564,
    "height": 334.41428571428565
  }
  first.append(schemeFirst)

  with open('monzoFirst.json', 'w') as outfile:
      json.dump(first, outfile)
  outfile.close()

  readFirst = tabula.read_pdf_with_template(""+pdfPath+"", "monzoFirst.json",output_format=None,encoding="utf-8", multiple_tables=False, pages="1", pandas_options={"header":None})

  middle = []
  for x in range(pagecount-1):

    scheme = {
      "page": x+2,
      "extraction_method": "guess",
      "x1": 27.08523679138161,
      "x2": 575.4869637811277,
      "y1": 22.695051116943358,
      "y2": 807.7205897521972,
      "width": 548.4017269897461,
      "height": 785.0255386352538
      }
    middle.append(scheme)
    if x+2 == pagecount-1:
      break


  with open('monzoMiddle.json', 'w') as outfile:
      json.dump(middle, outfile)
  outfile.close()

  readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "monzoMiddle.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})



  schemeLast = [{
      "page": pagecount,
      "extraction_method": "guess",
      "x1": 27.08523679138161,
      "x2": 575.4869637811277,
      "y1": 22.695051116943358,
      "y2": 807.7205897521972,
      "width": 548.4017269897461,
      "height": 785.0255386352538
      }]

  with open('monzoLast.json', 'w') as outfile:
      json.dump(schemeLast, outfile)
  outfile.close()
  #columns = [0,1,2,3]
  df1= pd.DataFrame(np.concatenate(readFirst))
  df2= pd.DataFrame(np.concatenate(readMiddle))

  if(pagecount>2):
      readLast = tabula.read_pdf_with_template(""+pdfPath+"", "monzoLast.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
      df3= pd.DataFrame(np.concatenate(readLast))
      new=df3[0].str.split(" ", n = 1, expand = True)

      df3[3]=df3[2]
      df3[2]=df3[1]
      df3[0]=new[0]
      df3[1]=new[1]
      final = pd.concat([df1, df2, df3], axis=0)
  else:
    new=df2[0].str.split(" ", n = 1, expand = True)
    df2[3]=df2[2]
    df2[2]=df2[1]
    df2[0]=new[0]
    df2[1]=new[1]
    final = pd.concat([df1, df2], axis=0)

  #print(df3)

  #print(final)
  final.to_excel(""+pdfPath+".xlsx",header=False)
  #os.remove("monsoeMiddle.json")



param1 = sys.argv[1]
convertmonzo(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)