from tkinter import *
from tkinter import messagebox
from random import *

def retour():
    global w, rect,text, segmentCourant,memoire, historiqueAjouts, historiqueSuppressions
    if(segmentCourant !=0):  
        segmentCourant-=1
        # pour chaque ajout de l'historique on le supprime
        for ajout in historiqueAjouts[segmentCourant]:
            annuleAjouteSegment(ajout[0])
        # pour chaque suppression de l'historique on l'ajoute
        for suppression in historiqueSuppressions[segmentCourant]:
            annuleSupprimeSegment(suppression[0],suppression[1])
            
        historiqueAjouts[segmentCourant]=[]
        historiqueSuppressions[segmentCourant]=[]
        majSegmentsLusEtALire()


def firstFit():
    global segmentCourant,segmentsALire,memoire,lru
    if(segmentCourant !=len(segmentsALire)):
        
        nSeg=segmentsALire[segmentCourant]
        #on regarde si le segment est déjà en mémoire
        if nSeg in memoire:        
            segmentCourant+=1
            majSegmentsLusEtALire()
        else:
            #on cherche le premier espace vide de taille suffisante               
            place=premierEspaceVide(tailleSeg[nSeg])
            while place==-1 :
                supprimeSegment(lru[0])
                lru=lru[1:]
                place=premierEspaceVide(tailleSeg[nSeg])
            
            res=ajouteSegment(nSeg,place)        
            
            if(res):
                segmentCourant+=1
                majSegmentsLusEtALire()

def bestFit():
    global segmentCourant,segmentsALire,memoire,lru
    if(segmentCourant !=len(segmentsALire)):
        
        nSeg=segmentsALire[segmentCourant]
        #on regarde si le segment est déjà en mémoire
        if nSeg in memoire:        
            segmentCourant+=1
            majSegmentsLusEtALire()
        else:
            #on cherche le premier espace vide de taille suffisante               
            place=meilleurEspaceVide(tailleSeg[nSeg])
            while place==-1 :
                supprimeSegment(lru[0])
                lru=lru[1:]
                place=premierEspaceVide(tailleSeg[nSeg])
            
            res=ajouteSegment(nSeg,place)        
            
            if(res):
                segmentCourant+=1
                majSegmentsLusEtALire()

    
def premierEspaceVide(taille):
    global memoire
    i=0
    nbVides=0
    while i <len(memoire) and nbVides<taille :
        if memoire[i]==-1:
            nbVides+=1
        else:
            nbVides=0
        i=i+1
    if (nbVides==taille):
        return (i-nbVides)
    else :
        return -1
        
        
def meilleurEspaceVide(taille):
    global memoire
    
    nbVides=0
    meilleur=len(memoire)+1
    iMeilleur=-1
    for i in range(len(memoire)) :
        if memoire[i]==-1:
            nbVides+=1
        else:
            if(nbVides>=taille and nbVides-taille<meilleur):
                meilleur=nbVides-taille
                iMeilleur=i-nbVides+1
            nbVides=0
    if(nbVides>=taille and nbVides-taille<meilleur):
        meilleur=nbVides-taille
        iMeilleur=i-nbVides+1    
    print(iMeilleur,meilleur)    
    return iMeilleur


def majSegmentsLusEtALire():
    global lus,alire,segmentCourant,segmentsALire,tailleSeg,lru,memoire
    
    segs1=segmentsALire[:segmentCourant]
    texte1="Lus : "
    for i in segs1:
        texte1+=str(i)+"("+str(tailleSeg[i])+"),"
    lus.config(text=texte1)
    
    segs2=segmentsALire[segmentCourant:]
    texte2="A lire : "
    for i in segs2:
        texte2+=str(i)+"("+str(tailleSeg[i])+"),"
    alire.config(text=texte2)
    
    #mise à jour lru
    lru=segs1[:]
    for i in range(nbSeg):
        n=lru.count(i)            
        #sil est en memoire, on enleve les premières occurrences
        if i in memoire:
            n=n-1
        for j in range(n):
            lru.remove(i)
      
   

def ajouteSegment(num,place):
    global rect,text,taille,memoire
    
    #♥on regarde si le segment est déjà en mémoire
    if (num in memoire):    
        messagebox.showerror("Ajout Segment","Le segment "+str(num)+" est déjà en mémoire")        
        return False
    #on regarde si le segment peut loger 
    if(place+tailleSeg[num]>nb):
        messagebox.showerror("Ajout Segment","Le segment "+str(num)+" ne peut être ajouté à la place "+str(place)+"\nIl n'y a pas assez de place")        
        return False
        
    #on regarde si le segment est ajoutable (y-a-t-il bien des vides ?)
    vide=True
    for i in range(place,place+tailleSeg[num]):
        if memoire[i]!=-1 :
            vide=False
    if(vide==False):
        messagebox.showerror("Ajout Segment","Le segment "+str(num)+" ne peut être ajouté à la place "+str(place)+"\nIl n'y a pas un vide de taille suffisante")        
        return False
        
    for i in range(place,place+tailleSeg[num]):
        memoire[i]=num
        
    rect[num]=w.create_rectangle(2+(taille+3)*place+3,5,2+(taille+3)*(place+tailleSeg[num])-3 ,taille+2,fill=coul[num])
    text[num]=w.create_text(2+(taille+3)*place+3+tailleSeg[num]*taille//2, 3+taille//2, text=str(num)) 

    #ajout à l'historique            
    historiqueAjouts[segmentCourant].append([num,place])

    return True
    
def annuleSupprimeSegment(num,place):
    global rect,text,taille,memoire
    
    for i in range(place,place+tailleSeg[num]):
        memoire[i]=num
        
    rect[num]=w.create_rectangle(2+(taille+3)*place+3,5,2+(taille+3)*(place+tailleSeg[num])-3 ,taille+2,fill=coul[num])
    text[num]=w.create_text(2+(taille+3)*place+3+tailleSeg[num]*taille//2, 3+taille//2, text=str(num)) 

    
def supprimeSegment(num):
    global rect,text,w,memoire

    #♥on regarde si le segment est en mémoire
    if not (num in memoire):    
        messagebox.showerror("Suppression Segment","Le segment "+str(num)+" n'est pas en mémoire")        
        return False
    
    debut=-1
    #on supprime le segment de la mémoire    
    for i in range(len( memoire)):
        if (memoire[i]==num):
            memoire[i]=-1
            if (debut==-1):
                debut=i
    # ...et de l'interface graphique
    w.delete(rect[num])
    w.delete(text[num])
    
    #ajout à l'historique            
    historiqueSuppressions[segmentCourant].append([num,debut])
    return True
 
def annuleAjouteSegment(num):
    global rect,text,w,memoire
    
    #on supprime le segment de la mémoire    
    for i in range(len( memoire)):
        if (memoire[i]==num):
            memoire[i]=-1
            
    # ...et de l'interface graphique
    w.delete(rect[num])
    w.delete(text[num])
    
       

nb =20 # nombre de pages en mémoire virtuelle
nbSeg =10 # nombre de segments
taille=50 # taille en pixel d'une page
rect=nbSeg*[0] 
text=nbSeg*[0]
coul=["red","yellow","green","pink","blue","brown","grey","orange","violet","magenta"]
tailleSeg=[1,3,5,2,7,8,1,3,2,4]

segmentsALire=[0,2,0,5,0,3,6,5,7,5,8,9,3,9,2,5,4,9]
segmentCourant=0
lru=[]

historiqueAjouts=len(segmentsALire)*[0]
historiqueSuppressions=len(segmentsALire)*[0]
for i in range(len(segmentsALire)):
    historiqueAjouts[i]=[]
    historiqueSuppressions[i]=[]

memoire=nb*[-1]


#construction de l'interface graphique
master = Tk()

w = Canvas(master, width=taille*nb+3*(nb-1)+4, height=taille+4)
w.pack()

#on créé le cadre
w.create_rectangle(2, 2, taille*nb+3*(nb-1)+5, taille+5)
for i in range (1,nb):
    w.create_line(2+(taille+3)*i,2,2+(taille+3)*i ,taille+5,fill="black",dash=(4,4))
   
b0 = Button(master, text="First Fit", command=firstFit)
b0.pack()

b1 = Button(master, text="Best Fit", command=bestFit)
b1.pack()

b2 = Button(master, text="Retour", command=retour)
b2.pack()

labelframe = LabelFrame(master, text="Numéros de segments")
labelframe.pack(fill="both", expand="yes")
 
lus = Label(labelframe, text="Lus : []")
lus.pack()

alire = Label(labelframe, text="ALire : "+str(segmentsALire))
alire.pack()
majSegmentsLusEtALire()

mainloop()

