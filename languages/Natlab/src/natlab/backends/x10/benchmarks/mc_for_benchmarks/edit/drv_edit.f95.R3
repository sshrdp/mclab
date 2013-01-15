MODULE Mod_drv_edit
CONTAINS

SUBROUTINE editdist(s1, s2, d1)
CHARACTER(LEN=*) :: s1
CHARACTER(LEN=*) :: s2
DOUBLE PRECISION :: d1
INTEGER :: tmpvar27
INTEGER :: tmpvar26
INTEGER :: Repl
INTEGER :: InsCost
INTEGER :: ReplCost
INTEGER :: i3
INTEGER :: i2
INTEGER :: i1
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: D
INTEGER :: n1
INTEGER :: j2
INTEGER :: j3
INTEGER :: n2
DOUBLE PRECISION :: tmpvar44
INTEGER :: j1
DOUBLE PRECISION, DIMENSION(3) :: tmpvar45
DOUBLE PRECISION :: tmpvar41
DOUBLE PRECISION :: tmpvar38
INTEGER :: DelCost









































! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the edit distance between the
! %	source string s1 and the target string s2.
! %
! %	The "edit distance" is the minimum number of single-character
! %	edit operations (deletions, insertions, replacements) that
! %	would convert s1 into s2. Method based on dynamic programming.
! %
! %	Invocation:
! %		>> d = editdist(s1, s2)
! %
! %		where
! %
! %		i. s1 is the source string,
! %
! %		i. s2 is the target string,
! %
! %		o. d is the edit distance.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> d = editdist('cow', 'horse')
! %
! %	Source:
! %		MATLAB 5 user contributed M-Files at
! %		http://www.mathworks.com/support/ftp/.
! %		("Anything Not Otherwise Categorized" category).
! %
! %	Author:
! %		Miguel A. Castro (talk2miguel@yahoo.com).
! %
! %	Date:
! %		June 2000.
! %
! %-----------------------------------------------------------------------
! % Cost of a deletion.
DelCost = 1;

! % Cost of an insertion.
InsCost = 1;

! % Cost of a replacement.
ReplCost = 1;
n1 = LEN(s1);
n2 = LEN(s2);

tmpvar26 = (n1 + 1);
tmpvar27 = (n2 + 1);
IF ((.NOT. ALLOCATED(D))) THEN 
	ALLOCATE(D(tmpvar26, tmpvar27))
END IF
! % Set up and initialize the dynamic programming table.
D = 0;
DO i3 = 1 , n1
	
	D((i3 + 1), 1) = (D(i3, 1) + DelCost);
END DO

i1 = i3
DO j3 = 1 , n2
	
	D(1, (j3 + 1)) = (D(1, j3) + InsCost);
END DO

j1 = j3
DO i2 = 1 , n1
	
	
	
	DO j2 = 1 , n2
	IF ((s1(i2:i2) == s2(j2:j2))) THEN 
	Repl = 0;
ELSE 
	Repl = ReplCost;
END IF
	
	tmpvar38 = (D(i2, j2) + Repl);
	tmpvar41 = (D((i2 + 1), j2) + DelCost);
	tmpvar44 = (D(i2, (j2 + 1)) + InsCost);
	tmpvar45 = [tmpvar38, tmpvar41, tmpvar44];
	! ...
D((i2 + 1), (j2 + 1)) = min(tmpvar45(1), tmpvar45(2), tmpvar45(3));
END DO
	j1 = j2
END DO



i1 = i2
d1 = D((n1 + 1), (n2 + 1));
IF (ALLOCATED(D)) THEN 
	DEALLOCATE(D)
END IF
END SUBROUTINE editdist

END MODULE Mod_drv_edit

PROGRAM drv_edit
USE Mod_drv_edit
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(3) :: valid
CHARACTER(2856) :: s1_2
CHARACTER(686) :: s1_1
CHARACTER(2882) :: s1_3
CHARACTER(10) :: arg_buffer
CHARACTER(1093) :: s2_1
CHARACTER(1986) :: s2_3
CHARACTER(2902) :: s2_2
DOUBLE PRECISION :: tmpvar25
INTEGER, DIMENSION(1, 6) :: tmpvar23
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
DOUBLE PRECISION :: timing_clock
INTEGER :: use
DOUBLE PRECISION :: d
INTEGER :: int_tmpvar
INTEGER :: i
INTEGER, DIMENSION(9) :: t
INTEGER :: scale1
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar49
scale = scale1

timing_clock = 0.0
int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale1
END IF
END DO




! % 
! %  Driver for the edit distance between two strings.
! % 
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s1_1 = 'Once upon a midnight dreary, while I pondered weak and weary,\n'//'Ov&
&er many a quaint and curious volume of forgotten lore,\n'//'While I no&
&dded, nearly napping, suddenly there came a tapping,\n'//'As of some o&
&ne gently rapping, rapping at my chamber door.\n'//'`"Tis some visitor&
&," I muttered, `tapping at my chamber door -\n'//'Only this, and nothi&
&ng more."\n'//'\n'//'Ah, distinctly I remember it was in the bleak Dec&
&ember,\n'//'And each separate dying ember wrought its ghost upon the f&
&loor.\n'//'Eagerly I wished the morrow; - vainly I had sought to borro&
&w\n'//'From my books surcease of sorrow - sorrow for the lost Lenore -&
&\n'//'For the rare and radiant maiden whom the angels named Lenore -\n&
&'//'Nameless here for evermore.\n'//'\n';
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s1_2 = 'And the silken sad uncertain rustling of each purple curtain\n'//'Thr&
&illed me - filled me with fantastic terrors never felt before;\n'//'So&
& that now, to still the beating of my heart, I stood repeating\n'//'`"&
&Tis some visitor entreating entrance at my chamber door -\n'//'Some la&
&te visitor entreating entrance at my chamber door; -\n'//'This it is, &
&and nothing more,"\n'//'\n'//'Presently my soul grew stronger; hesitat&
&ing then no longer,\n'//'`Sir," said I, `or Madam, truly your forgiven&
&ess I implore;\n'//'But the fact is I was napping, and so gently you c&
&ame rapping,\n'//'And so faintly you came tapping, tapping at my chamb&
&er door,\n'//'That I scarce was sure I heard you" - here I opened wide&
& the door; -\n'//'Darkness there, and nothing more.\n'//'\n'//'Deep in&
&to that darkness peering, long I stood there wondering, fearing,\n'//'&
&Doubting, dreaming dreams no mortal ever dared to dream before\n'//'Bu&
&t the silence was unbroken, and the darkness gave no token,\n'//'And t&
&he only word there spoken was the whispered word, `Lenore!"\n'//'This &
&I whispered, and an echo murmured back the word, `Lenore!"\n'//'Merely&
& this and nothing more.\n'//'\n'//'Back into the chamber turning, all &
&my soul within me burning,\n'//'Soon again I heard a tapping somewhat &
&louder than before.\n'//'`Surely," said I, `surely that is something a&
&t my window lattice;\n'//'Let me see then, what thereat is, and this m&
&ystery explore -\n'//'Let my heart be still a moment and this mystery &
&explore; -\n'//'"Tis the wind and nothing more!"\n'//'\n'//'Open here &
&I flung the shutter, when, with many a flirt and flutter,\n'//'In ther&
&e stepped a stately raven of the saintly days of yore.\n'//'Not the le&
&ast obeisance made he; not a minute stopped or stayed he;\n'//'But, wi&
&th mien of lord or lady, perched above my chamber door -\n'//'Perched &
&upon a bust of Pallas just above my chamber door -\n'//'Perched, and s&
&at, and nothing more.\n'//'\n'//'Then this ebony bird beguiling my sad&
& fancy into smiling,\n'//'By the grave and stern decorum of the counte&
&nance it wore,\n'//'`Though thy crest be shorn and shaven, thou," I sa&
&id, `art sure no craven.\n'//'Ghastly grim and ancient raven wandering&
& from the nightly shore -\n'//'Tell me what thy lordly name is on the &
&Night"s Plutonian shore!"\n'//'Quoth the raven, `Nevermore."\n'//'\n'/&
&/'Much I marvelled this ungainly fowl to hear discourse so plainly,\n'&
&//'Though its answer little meaning - little relevancy bore;\n'//'For &
&we cannot help agreeing that no living human being\n'//'Ever yet was b&
&lessed with seeing bird above his chamber door -\n'//'Bird or beast ab&
&ove the sculptured bust above his chamber door,\n'//'With such name as&
& `Nevermore."\n'//'\n'//'But the raven, sitting lonely on the placid b&
&ust, spoke only,\n'//'That one word, as if his soul in that one word h&
&e did outpour.\n'//'Nothing further then he uttered - not a feather th&
&en he fluttered -\n'//'Till I scarcely more than muttered `Other frien&
&ds have flown before -\n'//'On the morrow he will leave me, as my hope&
&s have flown before."\n'//'Then the bird said, `Nevermore."\n'//'\n';
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s1_3 = 'Startled at the stillness broken by reply so aptly spoken,\n'//'`Doub&
&tless," said I, `what it utters is its only stock and store,\n'//'Caug&
&ht from some unhappy master whom unmerciful disaster\n'//'Followed fas&
&t and followed faster till his songs one burden bore -\n'//'Till the d&
&irges of his hope that melancholy burden bore\n'//'Of "Never-nevermore&
&.""\n'//'\n'//'But the raven still beguiling all my sad soul into smil&
&ing,\n'//'Straight I wheeled a cushioned seat in front of bird and bus&
&t and door;\n'//'Then, upon the velvet sinking, I betook myself to lin&
&king\n'//'Fancy unto fancy, thinking what this ominous bird of yore -\&
&n'//'What this grim, ungainly, ghastly, gaunt, and ominous bird of yor&
&e\n'//'Meant in croaking `Nevermore."\n'//'\n'//'This I sat engaged in&
& guessing, but no syllable expressing\n'//'To the fowl whose fiery eye&
&s now burned into my bosom"s core;\n'//'This and more I sat divining, &
&with my head at ease reclining\n'//'On the cushion"s velvet lining tha&
&t the lamp-light gloated o"er,\n'//'But whose velvet violet lining wit&
&h the lamp-light gloating o"er,\n'//'She shall press, ah, nevermore!\n&
&'//'\n'//'Then, methought, the air grew denser, perfumed from an unsee&
&n censer\n'//'Swung by Seraphim whose foot-falls tinkled on the tufted&
& floor.\n'//'`Wretch," I cried, `thy God hath lent thee - by these ang&
&els he has sent thee\n'//'Respite - respite and nepenthe from thy memo&
&ries of Lenore!\n'//'Quaff, oh quaff this kind nepenthe, and forget th&
&is lost Lenore!"\n'//'Quoth the raven, `Nevermore."\n'//'\n'//'`Prophe&
&t!" said I, `thing of evil! - prophet still, if bird or devil! -\n'//'&
&Whether tempter sent, or whether tempest tossed thee here ashore,\n'//&
&'Desolate yet all undaunted, on this desert land enchanted -\n'//'On t&
&his home by horror haunted - tell me truly, I implore -\n'//'Is there &
&- is there balm in Gilead? - tell me - tell me, I implore!"\n'//'Quoth&
& the raven, `Nevermore."\n'//'\n'//'`Prophet!" said I, `thing of evil!&
& - prophet still, if bird or devil!\n'//'By that Heaven that bends abo&
&ve us - by that God we both adore -\n'//'Tell this soul with sorrow la&
&den if, within the distant Aidenn,\n'//'It shall clasp a sainted maide&
&n whom the angels named Lenore -\n'//'Clasp a rare and radiant maiden,&
& whom the angels named Lenore?"\n'//'Quoth the raven, `Nevermore."\n'/&
&/'\n'//'`Be that word our sign of parting, bird or fiend!" I shrieked &
&upstarting -\n'//'`Get thee back into the tempest and the Night"s Plut&
&onian shore!\n'//'Leave no black plume as a token of that lie thy soul&
& hath spoken!\n'//'Leave my loneliness unbroken! - quit the bust above&
& my door!\n'//'Take thy beak from out my heart, and take thy form from&
& off my door!"\n'//'Quoth the raven, `Nevermore."\n'//'\n'//'And the r&
&aven, never flitting, still is sitting, still is sitting\n'//'On the p&
&allid bust of Pallas just above my chamber door;\n'//'And his eyes hav&
&e all the seeming of a demon"s that is dreaming,\n'//'And the lamp-lig&
&ht o"er him streaming throws his shadow on the floor;\n'//'And my soul&
& from out that shadow that lies floating on the floor\n'//'Shall be li&
&fted - nevermore!\n';
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s2_1 = '"The Raven" is a narrative poem by the American writer Edgar Allan Po&
&e, first\n'//'published in January 1845. It is noted for its musicalit&
&y, stylized language,\n'//'and supernatural atmosphere. It tells of a &
&talking raven"s mysterious visit to\n'//'a distraught lover, tracing t&
&he latter"s slow descent into madness. The lover,\n'//'often identifie&
&d as being a student,[1][2] is lamenting the loss of his love,\n'//'Le&
&nore. The raven, sitting on a bust of Pallas, seems to further instiga&
&te his\n'//'distress with its constant repetition of the word, "Neverm&
&ore." Throughout the\n'//'poem, Poe makes allusions to folklore and va&
&rious classical works.\n'//'\n'//'Poe claimed to have written the poem&
& very logically and methodically. His intention\n'//'was to create a p&
&oem that would appeal to both critical and popular tastes, as\n'//'he &
&explains in the follow-up essay: "The Philosophy of Composition". The &
&poem\n'//'was inspired in part by a talking raven in the novel Barnaby&
& Rudge: A Tale of\n'//'the Riots of "Eighty by Charles Dickens.Poe bor&
&rows the complex rhythm and\n'//'meter of Elizabeth Barrett"s poem "La&
&dy Geraldine"s Courtship".\n'//'\n';
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s2_2 = 'The first publication of "The Raven" on January 29, 1845, in the New &
&York Evening\n'//'Mirror made Poe widely popular in his lifetime. The &
&poem was soon reprinted,\n'//'parodied, and illustrated. Though some c&
&ritics disagree about the value of the\n'//'poem, it remains one of th&
&e most famous poems ever written.\n'//'\n'//'\n'//'The Raven\n'//'\n'/&
&/'Once upon a midnight dreary, while I pondered, weak and weary,\n'//'&
&Over many a quaint and curious volume of forgotten lore,\n'//'While I &
&nodded, nearly napping, suddenly there came a tapping,\n'//'As of some&
& one gently rapping, rapping at my chamber door.\n'//'""Tis some visit&
&or," I muttered, "tapping at my chamber door �\n'//'            Only&
& this, and nothing more."\n'//'\n'//'Ah, distinctly I remember it was &
&in the bleak December,\n'//'And each separate dying ember wrought its &
&ghost upon the floor.\n'//'Eagerly I wished the morrow; � vainly I h&
&ad sought to borrow\n'//'From my books surcease of sorrow � sorrow f&
&or the lost Lenore �\n'//'For the rare and radiant maiden whom the a&
&ngels name Lenore �\n'//'            Nameless here for evermore.\n'/&
&/'\n'//'And the silken sad uncertain rustling of each purple curtain\n&
&'//'Thrilled me � filled me with fantastic terrors never felt before&
&;\n'//'So that now, to still the beating of my heart, I stood repeatin&
&g,\n'//'""Tis some visitor entreating entrance at my chamber door �\&
&n'//'Some late visitor entreating entrance at my chamber door; �\n'/&
&/'            This it is, and nothing more."\n'//'\n'//'Presently my s&
&oul grew stronger; hesitating then no longer,\n'//'"Sir," said I, "or &
&Madam, truly your forgiveness I implore;\n'//'But the fact is I was na&
&pping, and so gently you came rapping,\n'//'And so faintly you came ta&
&pping, tapping at my chamber door,\n'//'That I scarce was sure I heard&
& you"� here I opened wide the door; �\n'//'            Darkness th&
&ere, and nothing more.\n'//'...\n'//'\n'//'\n'//'"The Raven" follows a&
&n unnamed narrator who sits reading "forgotten lore"\n'//'as a method &
&to forget the loss of his love, Lenore. A "rapping at chamber\n'//'doo&
&r" reveals nothing, but excites his soul to "burning". A similar rappi&
&ng,\n'//'slightly louder, is heard at his window. When he goes to inve&
&stigate, a raven\n'//'steps into his chamber. Paying no attention to t&
&he man, the raven perches on a\n'//'bust of Pallas.\n'//'\n'//'Amused &
&by the raven"s comically serious disposition, the man demands that the&
&\n'//'bird tell him its name. The raven"s only answer is "Nevermore." &
&The narrator\n'//'is surprised that the raven can talk, though it will&
& not say anything further.\n'//'He reasons that the bird learned the w&
&ord "Nevermore" from some "unhappy master"\n'//'and that it is the onl&
&y word it knows. The narrator remarks that his "friend"\n'//'the raven&
& will soon fly out of his life, just as "other friends have flown\n'//&
&'before" along with his previous hopes. As if answering, the raven res&
&ponds\n'//'again with "Nevermore". The narrator is convinced that this&
& single word,\n'//'possibly learned from a previous owner with bad luc&
&k, is all that the bird can\n'//'say.\n';
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
! ...
s2_3 = '\n'//'Even so, the narrator pulls his chair directly in front of the &
&raven, determined\n'//'to learn more about it. He thinks for a moment,&
& not saying anything, but his mind\n'//'wanders back to his lost Lenor&
&e. He thinks the air grows denser and feels the\n'//'presence of angel&
&s. Confused by the association of the angels with the bird, the\n'//'n&
&arrator becomes angry, calling the raven a "thing of evil" and a "prop&
&het". As\n'//'he yells at the raven it only responds, "Nevermore." Fin&
&ally, he asks the raven\n'//'if he will be reunited with Lenore in hea&
&ven. When the raven responds with its\n'//'typical "Nevermore", he shr&
&ieks and commands the raven to return to the "Plutonian\n'//'shore", t&
&hough it never moves. Presumably at the time of the poem"s recitation\&
&n'//'by the narrator, the raven "still is sitting" on the bust of Pall&
&as. The\n'//'narrator"s final admission is that his soul is trapped be&
&neath the raven"s shadow\n'//'and shall be lifted "Nevermore".\n'//'\n&
&'//'\n'//'Analysis\n'//'\n'//'Poe wrote the poem as a narrative, witho&
&ut intentionally creating an allegory\n'//'or falling into didacticism&
&. The main theme of the poem is one of undying\n'//'devotion. The narr&
&ator experiences a perverse conflict between desire to\n'//'forget and&
& desire to remember. He seems to get some pleasure from focusing\n'//'&
&on loss. The narrator assumes that the word "Nevermore" is the raven"s&
&\n'//'"only stock and store", and yet he continues to ask it questions&
&, knowing\n'//'what the answer will be. His questions, then, are purpo&
&sely self-deprecating\n'//'and further incite his feelings of loss. Po&
&e leaves it unclear if the raven\n'//'actually knows what it is saying&
& or if it really intends to cause a reaction\n'//'in the poem"s narrat&
&or. The narrator begins as weak and weary, becomes\n'//'regretful and &
&grief-stricken, before passing into a frenzy and, finally,\n'//'madnes&
&s.\n'//'The raven perches on a bust of Pallas, a symbol of wisdom mean&
&t to imply the\n'//'narrator is a scholar.\n'//'The raven perches on a&
& bust of Pallas, a symbol of wisdom meant to imply the narrator is a s&
&cholar.\n'//'\n';





























! % define a bunch of strings with their associated execution approximate times (in scales)
! % s1{1} = [s1_1,s1_2,s1_3];
! % s2{1} = [s2_1,s2_2,s2_3];
! % t{1} = 75;
! % s1{2} = [s1_2];
! % s2{2} = [s2_2];
! % t{2}= 15;
! % s1{3} = [s1_1,s1_2,s1_3];
! % s2{3} = [s2_1];
! % t{3}= 13;
! % s1{4} = [s1_1,s1_1,s1_1];
! % s2{4} = [s1_1,s2_3];
! % t{4}= 10;
! % s1{5} = [s1_1];
! % s2{5} = [s2_1,s2_2,s2_3];
! % t{5}= 8;
! % s1{6} = [s1_1,s1_2];
! % s2{6} = [s2_1];
! % t{6}= 7;
! % s1{7} = [s1_2];
! % s2{7} = [s2_1];
! % t{7}= 6;
! % s1{8} = [s1_1];
! % s2{8} = [s2_2];
! % t{8}= 4;
! % s1{9} = [s1_1];
! % s2{9} = [s2_1];
! % t{9}= 2;
! % using array for t
t(1) = 75;
t(2) = 15;
t(3) = 13;
t(4) = 10;
t(5) = 8;
t(6) = 7;
t(7) = 6;
t(8) = 4;
t(9) = 2;
timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

DO  
	
	
	IF ((scale > 0)) THEN 
	
	! % d = editdist(S1, S2);
! % find the current string to used based on 'scales' left
use = 9;
	DO i = SIZE(t, 1) , 1 , (-1)
	
	IF ((t(i) <= scale)) THEN 
	use = i;
END IF
END DO
	
	SELECT CASE (use)
CASE (1)
	
	
	CALL editdist(s1_1//s1_2//s1_3, s2_1//s2_2//s2_3, d)
CASE (2)
	CALL editdist(s1_2, s2_2, d)
CASE (3)
	CALL editdist(s1_1//s1_2//s1_3, s2_1, d)
CASE (4)
	CALL editdist(s1_1//s1_1//s1_1, s1_1//s2_3, d)
CASE (5)
	CALL editdist(s1_1, s2_1//s2_2//s2_3, d)
CASE (6)
	CALL editdist(s1_1//s1_2, s2_1, d)
CASE (7)
	CALL editdist(s1_2, s2_1, d)
CASE (8)
	CALL editdist(s1_1, s2_2, d)
CASE (9)
	CALL editdist(s1_1, s2_1, d)
END SELECT
	
	scale = (scale - t(use));
ELSE 
	EXIT
END IF
	
	
END DO


CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar23(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar49 = MATMUL((t2 - t1), TRANSPOSE(tmpvar23))
! % Compute the running time in seconds
time = tmpvar49(1, 1);

tmpvar25 = d;
! % Store the benchmark output
output = [tmpvar25];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
END PROGRAM drv_edit

